package hexlet.code.mapper;

import hexlet.code.dto.UserCreateDTO;
import hexlet.code.dto.UserDTO;
import hexlet.code.dto.UserUpdateDTO;
import hexlet.code.model.User;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
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
    public abstract User map(UserCreateDTO model);

    @Mapping(target = "password", ignore = true)
    public abstract UserDTO map(User model);

    @Mapping(target = "passwordDigest", source = "password")
    public abstract User map(UserDTO model);

    @Mapping(target = "passwordDigest", source = "password")
    public abstract void update(UserUpdateDTO update, @MappingTarget User destination);

    @BeforeMapping
    public void encryptPassword(UserCreateDTO data) {
        var password = data.getPassword();
        data.setPassword(encoder.encode(password));
    }

    @BeforeMapping
    public void encryptPasswordUpdate(UserUpdateDTO update, @MappingTarget User destination) {
        var password = update.getPassword();
        if (password != null && password.isPresent()) {
            destination.setPasswordDigest(encoder.encode(password.get()));
        }
    }
}
