package majorfolio.backend.root.domain.content.entity;

import jakarta.persistence.*;
import majorfolio.backend.root.domain.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@Table(name = "Notifications")
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    private LocalDateTime timestamp;

}