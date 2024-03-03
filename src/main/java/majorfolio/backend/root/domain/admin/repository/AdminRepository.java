package majorfolio.backend.root.domain.admin.repository;

import majorfolio.backend.root.domain.admin.entity.Admin;
import majorfolio.backend.root.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByMember(Member member);
}
