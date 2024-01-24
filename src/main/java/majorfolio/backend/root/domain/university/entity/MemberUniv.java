package majorfolio.backend.root.domain.university.entity;


import jakarta.persistence.*;
import majorfolio.backend.root.domain.member.entity.Member;

@Entity
@Table(name = "UserUniv")
public class MemberUniv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subjects subject;
}
