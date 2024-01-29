package majorfolio.backend.root.domain.material.repository;

import majorfolio.backend.root.domain.material.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long> {

    List<Material> findTop5ByOrderByCreatedAtDesc();

    List<Material> findTop5ByOrderByTotalRecommendDesc();
}
