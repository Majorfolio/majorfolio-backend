package majorfolio.backend.root.domain.report.repository;

import majorfolio.backend.root.domain.report.entity.MemberReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberReportRepository extends JpaRepository<MemberReport, Long> {
}
