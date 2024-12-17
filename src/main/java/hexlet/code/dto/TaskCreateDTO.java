package hexlet.code.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
public class TaskCreateDTO {
    private Long id;
    private Integer index;
    private Long assigneeId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private String status;
    private Set<Long> taskLabelIds;
}
