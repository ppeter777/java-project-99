package hexlet.code.controller.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.LabelDTO;
import hexlet.code.dto.TaskStatusDTO;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.util.ModelGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import java.util.List;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskStatusesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper om;

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;

    private TaskStatus testTaskStatus;

    @BeforeEach
    public void setUp() {
        token = jwt().jwt(builder -> builder.subject("hexlet@example.com"));
        testTaskStatus = Instancio.of(modelGenerator.getTaskStatusModel()).create();
        taskStatusRepository.save(testTaskStatus);
    }

    @AfterEach
    public void clearRepositories() {
        taskStatusRepository.deleteAll();
    }

    @Test
    public void testIndex() throws Exception {
        var response = mockMvc.perform(get("/api/task_statuses").with(token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        var body = response.getContentAsString();
        List<TaskStatus> taskStatuses = om.readValue(body, new TypeReference<>() { });
        var actual = taskStatuses.stream().toList();
        var expected = taskStatusRepository.findAll();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testShow() throws Exception {
        var request = get("/api/task_statuses/" + testTaskStatus.getId()).with(token);
        var result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        var body = result.getResponse().getContentAsString();
        assertThatJson(body).and(
                v -> v.node("name").isEqualTo(testTaskStatus.getName()),
                v -> v.node("slug").isEqualTo(testTaskStatus.getSlug()));
    }

    @Test
    public void testCreate() throws Exception {
        var testTaskStatusDTO = new TaskStatusDTO();
        testTaskStatusDTO.setName("Test_task_status_name");
        testTaskStatusDTO.setSlug("Test_task_status_slug");
        var data = Instancio.of(modelGenerator.getTaskStatusModel()).create();
        var request = post("/api/task_statuses")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(testTaskStatusDTO));
        mockMvc.perform(request)
                .andExpect(status().isCreated());
        var createdTaskStatus = taskStatusRepository.getTaskStatusBySlug("Test_task_status_slug").get();
        assertNotNull(createdTaskStatus);
        assertThat(createdTaskStatus.getName()).isEqualTo("Test_task_status_name");
        assertThat(createdTaskStatus.getSlug()).isEqualTo("Test_task_status_slug");
    }

    @Test
    public void testUpdate() throws Exception {
        var testLabelDTO = new LabelDTO();
        testLabelDTO.setName("Test_label_name");
        var request = put("/api/task_statuses/" + testTaskStatus.getId())
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(testLabelDTO));
        mockMvc.perform(request)
                .andExpect(status().isOk());
        var updatedTaskStatus = taskStatusRepository.findById(testTaskStatus.getId()).orElseThrow();
        assertThat(updatedTaskStatus.getName()).isEqualTo(("Test_label_name"));
    }

    @Test
    public void testDelete() throws Exception {
        var id = testTaskStatus.getId();
        var request = delete("/api/task_statuses/" + id).with(token);
        assertThat(taskStatusRepository.findById(id).orElse(null)).isNotNull();
        mockMvc.perform(request)
                .andExpect(status().isNoContent());
        assertThat(taskStatusRepository.findById(id).orElse(null)).isNull();
    }

    @Test
    public void unauthorizedTest() throws Exception {
        mockMvc.perform(get("/api/task_statuses"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/api/task_statuses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(put("/api/task_statuses/" + testTaskStatus.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/task_statuses/" + testTaskStatus.getId()))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(delete("/api/task_statuses/" + testTaskStatus.getId()))
                .andExpect(status().isUnauthorized());
    }
}
