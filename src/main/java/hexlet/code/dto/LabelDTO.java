package hexlet.code.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hexlet.code.model.Task;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class LabelDTO {

    private Long id;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    private Set<Task> taskSet;
}
