/**
 * Like
 *
 * 2024.02.05
 *
 * 0.0.1
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import majorfolio.backend.root.domain.material.entity.Material;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Like DB정의
 *
 * @author 김영록
 * @version 0.0.1
 */
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @UpdateTimestamp
    private LocalDateTime date;

    private Boolean isCheck;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    public static Likes of(Material material, Member member, Boolean isCheck){
        return Likes.builder()
                .material(material)
                .member(member)
                .isCheck(isCheck)
                .build();
    }
}
