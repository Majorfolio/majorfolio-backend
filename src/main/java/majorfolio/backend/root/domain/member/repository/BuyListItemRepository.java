package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.member.entity.BuyList;
import majorfolio.backend.root.domain.member.entity.BuyListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuyListItemRepository extends JpaRepository<BuyListItem, Long> {
    List<BuyListItem> findAllByBuyListOrderByBuyInfoCreatedAtDesc(BuyList buyList);
}
