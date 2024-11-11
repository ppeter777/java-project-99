package hexlet.code.mapper;

import hexlet.code.dto.LabelDTO;
import hexlet.code.dto.TaskCreateDTO;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import org.mapstruct.*;

@Mapper(uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class LabelMapper {

    public abstract Label map(LabelDTO model);

    public abstract LabelDTO map(Label task);

    public abstract void update(LabelDTO update, @MappingTarget Label destination);

}
