package hexlet.code.component;

import hexlet.code.dto.UserCreateDTO;
import hexlet.code.dto.UserDTO;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.CustomUserDetailsService;
import hexlet.code.service.TaskStatusService;
import hexlet.code.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;

    @Autowired
    private final CustomUserDetailsService userService;

    @Autowired
    private final TaskStatusRepository taskStatusRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var email = "hexlet@example.com";
        var userData = new User();
        userData.setEmail(email);
        userData.setPasswordDigest("qwerty");
        userService.createUser(userData);

        var taskStatus1 = new TaskStatus();
        taskStatus1.setName("draft");
        taskStatus1.setSlug("draft");
        taskStatusRepository.save(taskStatus1);

        var taskStatus2 = new TaskStatus();
        taskStatus2.setName("To review");
        taskStatus2.setSlug("to_review");
        taskStatusRepository.save(taskStatus2);

        var taskStatus3 = new TaskStatus();
        taskStatus3.setName("To be fixed");
        taskStatus3.setSlug("to_be_fixed");
        taskStatusRepository.save(taskStatus3);

        var taskStatus4 = new TaskStatus();
        taskStatus4.setName("To publish");
        taskStatus4.setSlug("to_publish");
        taskStatusRepository.save(taskStatus4);

        var taskStatus5 = new TaskStatus();
        taskStatus5.setName("Published");
        taskStatus5.setSlug("published");
        taskStatusRepository.save(taskStatus5);
    }
}
