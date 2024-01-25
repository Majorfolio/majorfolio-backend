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
    @Column(name = "subject_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String professor;
    private String semester;
    private String major;

    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;


    public static Subjects of(String name, String professor, String semester, University university, String major){
        return Subjects.builder()
                .name(name)
                .professor(professor)
                .semester(semester)
                .university(university)
                .major(major)
                .build();
    }
}
