package majorfolio.backend.root.domain.analytics.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import majorfolio.backend.root.domain.assignment.entity.Assignment;
import majorfolio.backend.root.domain.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@Table(name = "Bookmarks")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Bookmarks {

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

    public static Bookmarks of(LocalDateTime timestamp, Member member, Assignment assignment){
        return Bookmarks.builder()
                .timestamp(timestamp)
                .member(member)
                .assignment(assignment)
                .build();
    }
}
