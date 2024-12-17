package hexlet.code.service;

import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskStatusService {

    @Autowired
    private TaskStatusRepository repository;

    @Autowired
    private UserUtils userUtils;

    public List<TaskStatus> getAll() {
        var taskStatuses = repository.findAll();
        return taskStatuses.stream()
                .toList();
    }
}
