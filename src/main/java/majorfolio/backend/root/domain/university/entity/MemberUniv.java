package majorfolio.backend.root.domain.university.entity;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import majorfolio.backend.root.domain.member.entity.Member;

import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Table(name = "UserUniv")
public class MemberUniv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String studentId;

    @OneToOne
    @JoinColumn(name = "major_id", nullable = false)
    private Major mainMajor;

    @OneToMany
    @JoinColumn(name = "major_id")
    private List<Major> subMajor;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    public static MemberUniv of(String studentId, Major mainMajor, List<Major> subMajor,
                                Member member, University university){
        return MemberUniv.builder()
                .studentId(studentId)
                .mainMajor(mainMajor)
                .subMajor(subMajor)
                .member(member)
                .university(university)
                .build();
    }

}
