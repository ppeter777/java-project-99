package hexlet.code.mapper;

import hexlet.code.dto.TaskCreateDTO;
import hexlet.code.dto.TaskDTO;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import java.util.List;
import java.util.Set;

@Mapper(uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class TaskMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "assignee", source = "assignee_id")
    @Mapping(target = "labels", source = "labelIds")
    @Mapping(target = "name", source = "title")
    @Mapping(target = "description", source = "content")
    public abstract Task map(TaskCreateDTO model);

    @Mapping(source = "taskStatus.slug", target = "status")
    @Mapping(source = "assignee.id", target = "assigneeId")
    @Mapping(source = "description", target = "content")
    @Mapping(source = "name", target = "title")
    @Mapping(source = "labels", target = "labelIds")
    public abstract TaskDTO map(Task task);

    @Mapping(target = "taskStatus", source = "status")
    @Mapping(target = "assignee", source = "assignee_id")
    @Mapping(target = "description", source = "content")
    @Mapping(target = "name", source = "title")
    public abstract void update(TaskCreateDTO update, @MappingTarget Task destination);

    public List<Long> map(Set<Label> labels) {
        if (labels == null) {
            return null;
        }
        return labels.stream()
                .map(Label::getId)
                .toList();
    }
}
