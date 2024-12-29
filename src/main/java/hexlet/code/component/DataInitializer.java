package hexlet.code.component;

import hexlet.code.dto.LabelDTO;
import hexlet.code.dto.TaskStatusDTO;
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

        TaskStatusDTO initTaskStatus1 = new TaskStatusDTO();
        initTaskStatus1.setName("Draft");
        initTaskStatus1.setSlug("draft");
        TaskStatusDTO initTaskStatus2 = new TaskStatusDTO();
        initTaskStatus2.setName("To review");
        initTaskStatus2.setSlug("to_review");
        TaskStatusDTO initTaskStatus3 = new TaskStatusDTO();
        initTaskStatus3.setName("To be fixed");
        initTaskStatus3.setSlug("to_be_fixed");
        TaskStatusDTO initTaskStatus4 = new TaskStatusDTO();
        initTaskStatus4.setName("To publish");
        initTaskStatus4.setSlug("to_publish");
        TaskStatusDTO initTaskStatus5 = new TaskStatusDTO();
        initTaskStatus5.setName("Published");
        initTaskStatus5.setSlug("published");
        List<TaskStatusDTO> initStatuses = new ArrayList<>(List.of(initTaskStatus1, initTaskStatus2,
            initTaskStatus3, initTaskStatus4, initTaskStatus5));

        for (TaskStatusDTO initTaskStatus : initStatuses) {
            var initName = initTaskStatus.getName();
            var initSlug = initTaskStatus.getSlug();
            if (taskStatusRepository.getTaskStatusByName(initName).isEmpty()
                    && taskStatusRepository.getTaskStatusBySlug(initSlug).isEmpty()) {
                taskStatusRepository.save(taskStatusMapper.map(initTaskStatus));
            }
        }
        LabelDTO initLabel1 = new LabelDTO();
        initLabel1.setName("bug");
        LabelDTO initLabel2 = new LabelDTO();
        initLabel2.setName("feature");
        List<LabelDTO> initLabels = new ArrayList<>(List.of(initLabel1, initLabel2));

        for (LabelDTO label : initLabels) {
            if (labelRepository.getLabelByName(label.getName()).isEmpty()) {
                labelRepository.save(labelMapper.map(label));
            }
        }
    }
}
