package hexlet.code.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class TaskCreateDTO {

    private Integer index;

    private String assignee_id;

    private String title;

    private String content;

    private String status;

    private List<Long> labelIds;
}
