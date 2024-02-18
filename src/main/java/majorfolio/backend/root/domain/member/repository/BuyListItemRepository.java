package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.member.entity.BuyList;
import majorfolio.backend.root.domain.member.entity.BuyListItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuyListItemRepository extends JpaRepository<BuyListItem, Long> {
    /**
     * 페이지와 구매리스트 아이디에 맞는 모든 구매자료들을 반환
     * @param buyList
     * @param pageable
     * @return
     */
    Page<BuyListItem> findAllByBuyListOrderByBuyInfoCreatedAtDesc(BuyList buyList, Pageable pageable);
    Page<BuyListItem> findAllByBuyListOrderByBuyInfoUpdatedAtDesc(BuyList buyList, Pageable pageable);

    BuyListItem findByBuyListAndMaterial(BuyList buyList, Material material);
}
