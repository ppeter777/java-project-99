package hexlet.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
public class TaskCreateDTO {
    private Long id;
    private Integer index;

    @JsonProperty("assignee_id")
    private Long assigneeId;

    private String title;
    private String content;
    private String status;
    private Set<Long> taskLabelIds;
}
