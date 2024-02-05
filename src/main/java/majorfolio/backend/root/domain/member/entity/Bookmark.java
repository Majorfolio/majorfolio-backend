/**
 * Bookmark
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
 * Bookmark DB정의
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
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
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

    public static Bookmark of(Material material, Member member, boolean isCheck) {
        return Bookmark.builder()
                .material(material)
                .member(member)
                .isCheck(isCheck)
                .build();
    }
}
