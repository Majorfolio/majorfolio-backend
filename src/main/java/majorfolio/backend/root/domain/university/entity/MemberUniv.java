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


    private String mainMajor;


    private String subMajor;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    public static MemberUniv of(String studentId, String mainMajor, String subMajor,
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
