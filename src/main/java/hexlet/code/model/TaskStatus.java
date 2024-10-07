package hexlet.code.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "task_status")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TaskStatus implements BaseEntity{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;

    @Column(unique = true)
    @NotNull
    @Size(min = 1)
    @ToString.Include
    private String name;

    @Column(unique = true)
    @NotNull
    @Size(min = 1)
    @ToString.Include
    private String slug;

    @CreatedDate
    private LocalDate createdAt;
}
