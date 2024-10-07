package hexlet.code.mapper;

import hexlet.code.dto.TaskStatusDTO;
import hexlet.code.dto.UserUpdateDTO;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import org.mapstruct.*;

@Mapper(uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class TaskStatusMapper {

    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
    public abstract TaskStatus map(TaskStatusDTO model);

    public abstract TaskStatusDTO map(TaskStatus task);

    public abstract void update(TaskStatusDTO update, @MappingTarget TaskStatus destination);
}
