package hexlet.code.mapper;

import hexlet.code.dto.LabelDTO;
import hexlet.code.model.Label;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class LabelMapper {

    public abstract Label map(LabelDTO model);

    public abstract LabelDTO map(Label task);

    public abstract List<LabelDTO> map(List<Label> list);

    public abstract void update(LabelDTO update, @MappingTarget Label destination);

}
