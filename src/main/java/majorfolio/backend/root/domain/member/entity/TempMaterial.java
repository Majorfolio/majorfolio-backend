package majorfolio.backend.root.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import majorfolio.backend.root.domain.material.entity.Material;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TempMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tempMaterial_id")
    private Long id;

    private String name;
    private String description;
    private String type;
    private String semester;
    private String professor;
    private String className;
    private String major;
    private String grade;
    private Integer score;
    private Integer fullScore;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private String link;


    @ManyToOne
    @JoinColumn(name = "tempStorage_id")
    private TempStorage tempStorage;

    public static TempMaterial of(
            String name,
            String description,
            String type,
            String semester,
            String professor,
            String className,
            String major,
            String grade,
            Integer score,
            Integer fullScore,
            String link,
            TempStorage tempStorage
    ){
        return TempMaterial.builder()
                .name(name)
                .description(description)
                .type(type)
                .semester(semester)
                .professor(professor)
                .className(className)
                .major(major)
                .grade(grade)
                .score(score)
                .fullScore(fullScore)
                .link(link)
                .tempStorage(tempStorage)
                .build();
    }
}
