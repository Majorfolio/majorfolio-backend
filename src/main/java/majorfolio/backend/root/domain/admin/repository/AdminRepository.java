package majorfolio.backend.root.domain.admin.repository;

import majorfolio.backend.root.domain.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

}
