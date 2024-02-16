/**
 * Preview
 *
 * 2024.02.05
 *
 * 0.0.1
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.material.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 미리보기 DB정의
 *
 * @author 김영록
 * @version 0.0.1
 */
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Preview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "preview_id")
    private Long id;
}
