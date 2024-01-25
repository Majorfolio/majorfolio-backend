package majorfolio.backend.root.domain.assignment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    private Long bookmarks;

    private Long totalSales;

    private LocalDateTime timestamp;

    public static AssignmentStats of(Assignment assignment, Long views, Long likes, Long bookmarks,
                                     Long totalSales, LocalDateTime timestamp){
        return AssignmentStats.builder()
                .assignment(assignment)
                .views(views)
                .likes(likes)
                .bookmarks(bookmarks)
                .totalSales(totalSales)
                .timestamp(timestamp)
                .build();
    }
}
