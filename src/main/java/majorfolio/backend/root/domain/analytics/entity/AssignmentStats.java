package majorfolio.backend.root.domain.analytics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import majorfolio.backend.root.domain.assignment.entity.Assignment;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "AssignmentStats")
public class AssignmentStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    private Long views;

    private Long likes;

    private LocalDateTime timestamp;

    public AssignmentStats(Long id) {
        this.id = id;
    }

}
