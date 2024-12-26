package hexlet.code.service;

import hexlet.code.dto.TaskStatusCreateDTO;
import hexlet.code.dto.TaskStatusDTO;
import hexlet.code.dto.TaskStatusUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskStatusMapper;
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

    @Autowired
    private TaskStatusMapper mapper;

    public List<TaskStatus> getAll() {
        var taskStatuses = repository.findAll();
        return taskStatuses.stream()
                .toList();
    }

    public TaskStatusDTO create(TaskStatusCreateDTO data) {
        var taskStatus = mapper.map(data);
        repository.save(taskStatus);
        return mapper.map(taskStatus);
    }

    public TaskStatusDTO update(TaskStatusUpdateDTO data, Long id) {
        var taskStatus = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task status with id = " + id + " not found"));
        mapper.update(data, taskStatus);
        repository.save(taskStatus);
        return mapper.map(taskStatus);
    }

    public TaskStatusDTO findById(Long id) {
        var taskStatus = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task status with id = " + id + " not found"));
        return mapper.map(taskStatus);
    }

    public void delete(Long id) {
        var taskStatus = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task status with id = " + id + " not found"));
        repository.delete(taskStatus);
    }
}
