/**
 * Majorfolio
 *
 * 2024.02.16
 *
 * 0.0.1
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.material.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 미리보기 이미지 콘텐츠들을 저장하고 있는 DB
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
public class PreviewImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "previewImages_id")
    private Long id;

    private String imageUrl;
    private int position;

    @ManyToOne
    @JoinColumn(name = "preview_id")
    private Preview preview;

    public static PreviewImages of(String imageUrl, int position, Preview preview){
        return PreviewImages.builder()
                .imageUrl(imageUrl)
                .position(position)
                .preview(preview)
                .build();
    }
}
