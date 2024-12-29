package hexlet.code.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LabelDTO {

    private Long id;

    @Size(min = 3, max = 1000)
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
}
