package majorfolio.backend.root.domain.material.entity;

import jakarta.persistence.*;
import lombok.*;
import majorfolio.backend.root.domain.member.entity.Member;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * material 테이블 생성
 * @author 김태혁
 * @version 0.0.1
 */
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id")
    private Long id;
    private String name;
    private String description;
    private String type;
    private String semester;
    private String professor;
    private int totalRecommend;
    private int totalBookmark;
    private String className;
    private String major;
    private String grade;
    private float score;
    private float fullScore;
    private int page;
    private String status;
    private int price;
    private String link;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "preview_id")
    private Preview preview;

    public static Material of(String name, String description, String type, String semester,
                              String professor, String className, String major,
                              String grade, float score, float fullScore, int page,
                              String link, Member member, Preview preview){
        return Material.builder()
                .name(name)
                .description(description)
                .type(type)
                .semester(semester)
                .professor(professor)
                .totalRecommend(0)
                .totalBookmark(0)
                .className(className)
                .major(major)
                .grade(grade)
                .score(score)
                .fullScore(fullScore)
                .page(page)
                .status("reviewing")
                .price(4700)
                .link(link)
                .member(member)
                .preview(preview)
                .build();
    }
}
