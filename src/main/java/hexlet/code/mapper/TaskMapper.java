package hexlet.code.mapper;

import hexlet.code.dto.TaskDTO;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import hexlet.code.repository.LabelRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class TaskMapper {

    @Autowired
    private LabelRepository labelRepository;

//    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "assignee", source = "assigneeId")
    @Mapping(target = "labels", source = "taskLabelIds", qualifiedByName = "taskLabelIdsToLabels")
    @Mapping(target = "name", source = "title")
    @Mapping(target = "description", source = "content")
    public abstract Task map(TaskDTO model);

//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "assignee", source = "assigneeId")
//    @Mapping(target = "labels", source = "taskLabelIds", qualifiedByName = "taskLabelIdsToLabels")
//    @Mapping(target = "name", source = "title")
//    @Mapping(target = "description", source = "content")
//    public abstract Task map(TaskDTO model);

    @Mapping(source = "taskStatus.slug", target = "status")
    @Mapping(source = "assignee.id", target = "assigneeId")
    @Mapping(source = "description", target = "content")
    @Mapping(source = "name", target = "title")
    @Mapping(source = "labels", target = "taskLabelIds", qualifiedByName = "labelsToTaskLabelIds")
    public abstract TaskDTO map(Task task);

    @Mapping(target = "taskStatus", source = "status")
    @Mapping(target = "assignee", source = "assigneeId")
    @Mapping(target = "description", source = "content")
    @Mapping(target = "name", source = "title")
    @Mapping(target = "labels", source = "taskLabelIds", qualifiedByName = "taskLabelIdsToLabels")
    public abstract void update(TaskDTO update, @MappingTarget Task destination);

    @Named("taskLabelIdsToLabels")
    public Set<Label> taskLabelIdsToLabels(Set<Long> labelIds) {
        return labelIds == null ? new HashSet<>() : labelRepository.findByIdIn(labelIds);
    }

    @Named("labelsToTaskLabelIds")
    public Set<Long> labelsToTaskLabelIds(Set<Label> labels) {
        return labels == null ? new HashSet<>()
                : labels.stream()
                .map(Label::getId)
                .collect(Collectors.toSet());
    }
}
