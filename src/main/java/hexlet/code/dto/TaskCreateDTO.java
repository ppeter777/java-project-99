package hexlet.code.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
public class TaskCreateDTO {
    private Long id;
    private Integer index;
    private Long assignee_id;
    private String title;
    private String content;
    private String status;
    private Set<Long> taskLabelIds;
}
