package majorfolio.backend.root.domain.content.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import majorfolio.backend.root.domain.assignment.entity.Assignment;
import majorfolio.backend.root.domain.member.entity.Member;

@Entity
@Table(name = "UploadLibraries")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UploadLibraries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    public static UploadLibraries of(Member member, Assignment assignment){
        return UploadLibraries.builder()
                .member(member)
                .assignment(assignment)
                .build();
    }
}