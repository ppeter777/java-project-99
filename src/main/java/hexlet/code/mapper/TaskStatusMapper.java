package hexlet.code.mapper;

import hexlet.code.dto.TaskStatusDTO;
import hexlet.code.model.TaskStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class TaskStatusMapper {

    @Mapping(target = "id", ignore = true)
    public abstract TaskStatus map(TaskStatusDTO model);

    public abstract TaskStatusDTO map(TaskStatus task);

    public abstract void update(TaskStatusDTO data, @MappingTarget TaskStatus destination);
}
