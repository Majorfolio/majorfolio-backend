package majorfolio.backend.root.domain.payments.repository;

import majorfolio.backend.root.domain.payments.entity.BuyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface BuyInfoRepository extends JpaRepository<BuyInfo, Long> {
    /**
     * 원하는 기간에 해당하는 자료 반환
     * @param startDate
     * @param endDate
     * @return
     */
    List<BuyInfo> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
