package majorfolio.backend.root.domain.material.entity;

import jakarta.persistence.*;
import lombok.*;
import majorfolio.backend.root.domain.member.entity.Member;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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
    private int score;
    private int fullScore;
    private int page;
    private String status;
    private String phoneNumber;
    private int price;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
