package majorfolio.backend.root.domain.university.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import majorfolio.backend.root.domain.member.entity.Member;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "user_univ")
public class MemberUniv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentId;
    private String major1;
    private String major2;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    public static MemberUniv of(String studentId, String major1, String major2, Member member,
                                University university){
        return MemberUniv
                .builder()
                .studentId(studentId)
                .major1(major1)
                .major2(major2)
                .member(member)
                .university(university)
                .build();
    }
}
