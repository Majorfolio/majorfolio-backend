package majorfolio.backend.root.domain.analytics.entity;

import jakarta.persistence.*;
import majorfolio.backend.root.domain.assignment.entity.Assignment;
import majorfolio.backend.root.domain.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@Table(name = "Likes")
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    private LocalDateTime timestamp;

    // Constructors, Getters, and Setters
}
