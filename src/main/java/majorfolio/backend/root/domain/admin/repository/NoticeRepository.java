package majorfolio.backend.root.domain.admin.repository;

import majorfolio.backend.root.domain.admin.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
