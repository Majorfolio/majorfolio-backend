package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.member.entity.BuyList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyListRepository extends JpaRepository<BuyList, Long> {
}
