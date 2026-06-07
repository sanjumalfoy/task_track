package sajeevan.task_track.Entity;

import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "task_history")
 // Standard Lombok getters/setters/constructors
@AllArgsConstructor
@NoArgsConstructor
public class TaskHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(name = "changed_field", nullable = false)
    private String changedField;

    @Column(name = "old_value")
    private String oldValue;

    @Column(name = "new_value")
    private String newValue;

    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    private LocalDateTime updatedAt = LocalDateTime.now();
}
