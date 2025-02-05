package hexlet.code.mapper;

import hexlet.code.dto.UserDTO;
import hexlet.code.model.User;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {

    @Autowired
    private PasswordEncoder encoder;

    @Mapping(target = "passwordDigest", source = "password")
    public abstract User map(UserDTO model);

    @Mapping(target = "password", ignore = true)
    public abstract UserDTO map(User model);

    @Mapping(target = "passwordDigest", source = "password")
    public abstract void update(UserDTO update, @MappingTarget User destination);

    @BeforeMapping
    public void encryptPassword(UserDTO data) {
        var password = data.getPassword();
        if (password != null) {
            data.setPassword(JsonNullable.of(encoder.encode(password.get())));
        }
    }

    @BeforeMapping
    public void encryptPasswordUpdate(UserDTO update, @MappingTarget User destination) {
        var password = update.getPassword();
        if (password != null) {
            destination.setPasswordDigest(encoder.encode(password.get()));
        }
    }
}
