package hexlet.code.component;

import hexlet.code.dto.LabelCreateDTO;
import hexlet.code.dto.TaskStatusCreateDTO;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;

    @Autowired
    private final CustomUserDetailsService userService;

    @Autowired
    private final TaskStatusRepository taskStatusRepository;

    @Autowired
    private final TaskStatusMapper taskStatusMapper;

    @Autowired
    private final LabelRepository labelRepository;

    @Autowired
    private final LabelMapper labelMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var email = "hexlet@example.com";
        if (userRepository.findByEmail(email).isEmpty()) {
            var userData = new User();
            userData.setEmail(email);
            userData.setPasswordDigest("qwerty");
            userService.createUser(userData);
        }

        List<TaskStatusCreateDTO> initStatuses = new ArrayList<>(List.of(
            new TaskStatusCreateDTO("Draft", "draft"),
            new TaskStatusCreateDTO("To review", "to_review"),
            new TaskStatusCreateDTO("To be fixed", "to_be_fixed"),
            new TaskStatusCreateDTO("To publish", "to_publish"),
            new TaskStatusCreateDTO("Published", "published")));

        for (TaskStatusCreateDTO status : initStatuses) {
            if (taskStatusRepository.getTaskStatusByName(status.getName()).isEmpty()) {
                taskStatusRepository.save(taskStatusMapper.map(status));
            }
        }

        List<LabelCreateDTO> initLabels = new ArrayList<>(List.of(
                new LabelCreateDTO("bug"),
                new LabelCreateDTO("feature")));

        for (LabelCreateDTO label : initLabels) {
            if (labelRepository.getLabelByName(label.getName()).isEmpty()) {
                labelRepository.save(labelMapper.map(label));
            }
        }
    }
}
