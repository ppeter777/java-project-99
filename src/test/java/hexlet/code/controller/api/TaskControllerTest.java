package hexlet.code.controller.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.TaskDTO;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.util.ModelGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
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
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TasksController tasksController;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private TaskMapper taskMapper;

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;
    private User testUser;
    private TaskStatus testTaskStatus;
    private Task testTask1;
    private Task testTask2;
    private Label label1;

    @BeforeEach
    public void setUp() {
        token = jwt().jwt(builder -> builder.subject("hexlet@example.com"));
        testTask1 = Instancio.of(modelGenerator.getTaskModel()).create();
        testTask2 = Instancio.of(modelGenerator.getTaskModel()).create();
        testTaskStatus = Instancio.of(modelGenerator.getTaskStatusModel()).create();
        testUser = Instancio.of(modelGenerator.getUserModel()).create();
        userRepository.save(testUser);
        testTaskStatus = taskStatusRepository.findAll().get(0);
        testTask1.setTaskStatus(testTaskStatus);
        label1 = labelRepository.findAll().getFirst();
        testTask1.setLabels(Set.of(label1));
        testTask2.setTaskStatus(testTaskStatus);
        testTask1.setAssignee(testUser);
        testTask2.setAssignee(testUser);
        taskRepository.save(testTask1);
        taskRepository.save(testTask2);
    }

    @Test
    public void testIndex() throws Exception {
        var response = mockMvc.perform(get("/api/tasks").with(token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        var body = response.getContentAsString();
        List<TaskDTO> taskDTOS = om.readValue(body, new TypeReference<>() { });
        List<Task> actual = taskDTOS.stream().map(taskMapper::map).toList();
        var expected = taskRepository.findAll();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testShow() throws Exception {
        var request = get("/api/tasks/" + testTask1.getId()).with(token);
        var result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        var body = result.getResponse().getContentAsString();
        assertThatJson(body).and(
                v -> v.node("title").isEqualTo(testTask1.getName()),
                v -> v.node("content").isEqualTo(testTask1.getDescription()));
    }

    @Test
    public void testCreate() throws Exception {
        var testTask = Instancio.of(modelGenerator.getTaskModel())
                .create();
        var testTaskDto = taskMapper.map(testTask);
        testTaskDto.setAssigneeId(testUser.getId());
        testTaskDto.setStatus(testTaskStatus.getSlug());
        var request = post("/api/tasks")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(testTaskDto));
        mockMvc.perform(request)
                .andExpect(status().isCreated());
        var task = taskRepository.getTaskByName(testTask.getName());
        assertNotNull(task);
        assertThat(task.get().getDescription()).isEqualTo(testTask.getDescription());
    }

    @Test
    public void testCreate2() throws Exception {
        var data = new TaskDTO();
        data.setTitle("New Name");
        data.setContent("New content");
        data.setStatus(testTaskStatus.getSlug());
        data.setAssigneeId(testUser.getId());
        var request = post("/api/tasks")
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));
        mockMvc.perform(request)
                .andExpect(status().isCreated());
        var task = taskRepository.getTaskByName(data.getTitle()).orElse(null);
        assertThat(task).isNotNull();
        assertThat(task.getName()).isEqualTo(data.getTitle());
        assertThat(task.getDescription()).isEqualTo(data.getContent());
        assertThat(task.getTaskStatus().getSlug()).isEqualTo(data.getStatus());
        assertThat(task.getAssignee().getId()).isEqualTo(data.getAssigneeId());
    }

    @Test
    public void testUpdate() throws Exception {
        var data = new HashMap<>();
        data.put("title", "test_name");
        data.put("content", "test_description");

        var request = put("/api/tasks/" + testTask1.getId())
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));
        mockMvc.perform(request)
                .andExpect(status().isOk());
        var updatedTask = taskRepository.findById(testTask1.getId()).get();
        assertThat(updatedTask.getName()).isEqualTo(("test_name"));
        assertThat(updatedTask.getDescription()).isEqualTo(("test_description"));
    }

    @Test
    public void testDelete() throws Exception {
        var id = testTask1.getId();
        var request = delete("/api/tasks/" + id)
                .with(token);
        assertThat(taskRepository.findById(id).orElse(null)).isNotNull();
        mockMvc.perform(request)
                .andExpect(status().isNoContent());
        var task = taskRepository.findById(id);
        assertThat(taskRepository.findById(id).orElse(null)).isNull();
    }

    @Test
    public void testFilter() throws Exception {
        var name = testTask1.getName();
        var assigneeId = testTask1.getAssignee().getId();
        var slug = testTask1.getTaskStatus().getSlug();
        var labelIds = testTask1.getLabels();
        var firstLabelId = labelIds.stream().findFirst().get().getId();
        var requestString = "/api/tasks?titleCont="
                + name
                + "&assigneeId="
                + assigneeId
                + "&status="
                + slug
                + "&labelId="
                + firstLabelId;
        var request = get(requestString)
                .with(token);
        var result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray().allSatisfy(element ->
                assertThatJson(element)
                        .and(v -> v.node("title").asString().containsIgnoringCase(name))
                        .and(v -> v.node("assigneeId").isEqualTo(assigneeId))
                        .and(v -> v.node("status").isEqualTo(slug))
                        .and(v -> v.node("labelIds").isArray()));
    }
}
