/**
 * PreviewImagesRepository
 *
 * 2024.02.16
 *
 * 0.0.1
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.material.entity.Preview;
import majorfolio.backend.root.domain.material.entity.PreviewImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * PreviewImages DB의 Repository
 *
 * @author 김영록
 * @version 0.0.1
 */
public interface PreviewImagesRepository extends JpaRepository<PreviewImages, Long> {

    PreviewImages findByPreviewAndPosition(Preview preview, int position);

    Long countByPreview(Preview preview);

}
