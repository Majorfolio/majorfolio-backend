package majorfolio.backend.root.domain.university.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Subjects")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Subjects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String professor;
    private String semester;

    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    public static Subjects of(String name, String professor, String semester, University university){
        return Subjects.builder()
                .name(name)
                .professor(professor)
                .semester(semester)
                .university(university)
                .build();
    }
}
