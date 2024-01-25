package majorfolio.backend.root.domain.assignment.repository;

import majorfolio.backend.root.domain.assignment.entity.Assignment;
import majorfolio.backend.root.domain.assignment.entity.AssignmentStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssignmentStatsRepository extends JpaRepository<AssignmentStats, Long> {
    AssignmentStats findByAssignment(Assignment assignment);
    @Query("SELECT a FROM AssignmentStats a JOIN FETCH a.assignment ORDER BY a.likes DESC limit 5")
    List<AssignmentStats> findTop5ByOrderByLikesDesc();
}
