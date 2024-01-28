
/**
 * University
 *
 * 0.0.1
 *
 * 2024.01.28
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.university.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * University DB테이블 정의
 *
 * @author 김영록
 * @version 0.0.1
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_id")
    private Long id;
    private String universityName;
    private String domain;

}
