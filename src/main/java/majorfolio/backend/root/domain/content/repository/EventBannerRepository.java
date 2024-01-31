package majorfolio.backend.root.domain.content.repository;

import majorfolio.backend.root.domain.content.entity.EventBanner;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * EventBanner의 데이터를 관리하기 위한 레포지토리 생성
 * @author 김태혁
 * @version 0.0.1
 */
public interface EventBannerRepository extends JpaRepository<EventBanner, Long> {

}
