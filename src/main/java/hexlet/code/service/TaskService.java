package hexlet.code.service;

import hexlet.code.dto.TaskCreateDTO;
import hexlet.code.dto.TaskDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    public TaskDTO create(TaskCreateDTO taskData) {
        var task = taskMapper.map(taskData);
        var slug = taskData.getStatus();
        var taskStatus = taskStatusRepository.getTaskStatusBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Task status not found"));
        task.setTaskStatus(taskStatus);
        taskRepository.save(task);
        return taskMapper.map(task);
    }

    public TaskDTO update(TaskCreateDTO taskData, Long id) {
        var task = taskRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        taskMapper.update(taskData, task);
        taskRepository.save(task);
        return taskMapper.map(task);
    }
}
