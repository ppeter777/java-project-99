package hexlet.code.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Setter
@Getter
public class UserDTO {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String password;
}
