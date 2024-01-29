package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.member.entity.SellList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellListRepository extends JpaRepository<SellList, Long> {
}
