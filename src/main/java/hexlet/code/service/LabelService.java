package hexlet.code.service;

import hexlet.code.dto.LabelDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LabelService {

    @Autowired
    LabelRepository labelRepository;

    @Autowired
    LabelMapper labelMapper;

    public LabelDTO create(LabelDTO data) {
        var label = labelMapper.map(data);
        labelRepository.save(label);
        return labelMapper.map(label);
    }

    public LabelDTO update(LabelDTO data, Long id) {
        var label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Label not found"));
        labelMapper.update(data, label);
        labelRepository.save(label);
        return labelMapper.map(label);
    }
}
