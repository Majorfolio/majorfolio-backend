package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.member.entity.SellListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellListItemRepository extends JpaRepository<SellListItem, Long> {
    Long countByMaterial(Material material);
}
