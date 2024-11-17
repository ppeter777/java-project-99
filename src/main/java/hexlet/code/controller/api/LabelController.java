package hexlet.code.controller.api;

import hexlet.code.dto.LabelDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.repository.LabelRepository;
import hexlet.code.service.LabelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LabelController {

    @Autowired
    LabelRepository repository;

    @Autowired
    LabelMapper labelMapper;

    @Autowired
    LabelService labelService;

    @GetMapping("/labels")
    ResponseEntity<List<LabelDTO>> index() {
        var labels = repository.findAll();
        var result = labels.stream()
                .map(labelMapper::map)
                .toList();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(labels.size()))
                .body(result);
    }

    @GetMapping("/labels/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelDTO show(@PathVariable Long id) {
        var label = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id + " Not Found"));
        return labelMapper.map(label);
    }

    @PostMapping("/labels")
    @ResponseStatus(HttpStatus.CREATED)
    LabelDTO create(@Valid @RequestBody LabelDTO labelData) {
        return labelService.create(labelData);
    }

    @PutMapping("/labels/{id}")
    @ResponseStatus(HttpStatus.OK)
    LabelDTO update(@RequestBody LabelDTO data, @PathVariable Long id) {
        return labelService.update(data, id);
    }

    @DeleteMapping("/labels/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void destroy(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
