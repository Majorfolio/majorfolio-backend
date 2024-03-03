package majorfolio.backend.root.domain.payments.repository;

import majorfolio.backend.root.domain.payments.entity.RefundInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository extends JpaRepository<RefundInfo, Long> {
    RefundInfo findByBuyInfoId(Long buyInfoId);
}
