package hexlet.code.controller.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.mapper.LabelMapper;
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
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LabelControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    LabelController tasksController;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LabelMapper labelMapper;

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;

    private Label testLabel;

    private User testUser;

    private TaskStatus testTaskStatus;

    private Task testTask;


    @BeforeEach
    public void setUp() {
        token = jwt().jwt(builder -> builder.subject("hexlet@example.com"));
        testLabel = Instancio.of(modelGenerator.getLabelModel())
                .create();
        testTask = Instancio.of(modelGenerator.getTaskModel())
                .create();
        testUser = Instancio.of(modelGenerator.getUserModel()).create();
        userRepository.save(testUser);
        testTaskStatus = taskStatusRepository.findAll().get(0);

        testTask.setTaskStatus(testTaskStatus);
        testTask.setAssignee(testUser);
        taskRepository.save(testTask);
        labelRepository.save(testLabel);
    }

    @Test
    public void testIndex() throws Exception {

        var response = mockMvc.perform(get("/api/labels").with(token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        var body = response.getContentAsString();

        List<Label> labels = om.readValue(body, new TypeReference<>() {
        });

        var actual = labels.stream().toList();
        var expected = labelRepository.findAll();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testShow() throws Exception {
        var request = get("/api/labels/" + testLabel.getId()).with(token);
        var result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        var body = result.getResponse().getContentAsString();
        assertThatJson(body).and(
                v -> v.node("name").isEqualTo(testLabel.getName()));
    }

    @Test
    public void testCreate() throws Exception {
        var testLabel = Instancio.of(modelGenerator.getLabelModel())
                .create();
        var testLabelDto = labelMapper.map(testLabel);
        testLabelDto.setName(testLabel.getName());
        var request = post("/api/labels")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(testLabelDto));
        mockMvc.perform(request)
                .andExpect(status().isCreated());

        var label = labelRepository.getLabelByName(testLabel.getName());

        assertNotNull(label);
        assertThat(label.get().getName()).isEqualTo(testLabel.getName());
    }

    @Test
    public void testUpdate() throws Exception {
        var data = new HashMap<>();
        data.put("name", "test_label_name");

        var request = put("/api/labels/" + testLabel.getId())
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var updatedLabel = labelRepository.findById(testLabel.getId()).get();
        assertThat(updatedLabel.getName()).isEqualTo(("test_label_name"));
    }

    @Test
    public void testDelete() throws Exception {
        var id = testLabel.getId();
        var request = delete("/api/labels/" + id)
                .with(token);
        assertThat(labelRepository.findById(id).orElse(null)).isNotNull();
        mockMvc.perform(request)
                .andExpect(status().isNoContent());
        assertThat(labelRepository.findById(id).orElse(null)).isNull();
    }
}
