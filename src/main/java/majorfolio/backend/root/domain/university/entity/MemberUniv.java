package majorfolio.backend.root.domain.university.entity;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import majorfolio.backend.root.domain.member.entity.Member;

@Entity
@Table(name = "UserUniv")
public class MemberUniv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String studentId;

    @Column(nullable = false)
    private String mainMajor;
    private String subMajor;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

}
