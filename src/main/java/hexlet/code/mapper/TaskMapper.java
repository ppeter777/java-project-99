package hexlet.code.mapper;

import hexlet.code.dto.TaskCreateDTO;
import hexlet.code.dto.TaskDTO;

import hexlet.code.model.Task;
import org.mapstruct.*;

@Mapper(uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class TaskMapper {
//
//    @Mapping(target = "taskStatus", source = "status")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "assignee", source = "assignee_id")
    @Mapping(target = "name", source = "title")
    @Mapping(target = "description", source = "content")
    public abstract Task map(TaskCreateDTO model);

    @Mapping(source = "taskStatus.slug", target = "status")
    @Mapping(source = "assignee.id", target = "assignee_id")
    @Mapping(source = "description", target = "content")
    @Mapping(source = "name", target = "title")
    public abstract TaskDTO map(Task task);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "assignee", source = "assignee_id")
//    @Mapping(target = "name", source = "title")
//    @Mapping(target = "description", source = "content")
//    @Mapping(target = "taskStatus", source = "status")
//    public abstract Task map(TaskCreateDTO model);


    @Mapping(target = "taskStatus", source = "status")
    @Mapping(target = "assignee", source = "assignee_id")
    @Mapping(target = "description", source = "content")
    @Mapping(target = "name", source = "title")
    public abstract void update(TaskCreateDTO update, @MappingTarget Task destination);
}
