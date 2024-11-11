package hexlet.code.controller.api;

import hexlet.code.dto.TaskCreateDTO;
import hexlet.code.dto.TaskDTO;
import hexlet.code.dto.TaskParamsDTO;
import hexlet.code.dto.TaskStatusDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.repository.TaskRepository;
import hexlet.code.service.TaskService;
import hexlet.code.specification.TaskSpecification;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TasksController {

    @Autowired
    public TaskRepository repository;

    @Autowired
    public TaskMapper taskMapper;

    @Autowired
    public TaskService taskService;

    @Autowired
    public TaskSpecification specBuilder;

    @GetMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO show(@PathVariable Long id) {
        var task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id + " Not Found"));
        return taskMapper.map(task);
    }

    @GetMapping("/tasks")
    ResponseEntity<List<TaskDTO>> index(TaskParamsDTO params, @RequestParam (defaultValue = "1") int page) {
        var specification = specBuilder.build(params);
        var tasks = repository.findAll(specification, PageRequest.of(page - 1, 10));
        var result = tasks.stream()
                .map(taskMapper::map)
                .toList();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(taskService.getAll().size()))
                .body(result);
    }

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    TaskDTO create(@Valid @RequestBody TaskCreateDTO taskData) {
        return taskService.create(taskData);
    }

    @PutMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    TaskDTO update(@RequestBody TaskCreateDTO data, @PathVariable Long id) {
        return taskService.update(data, id);
    }

    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void destroy(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
