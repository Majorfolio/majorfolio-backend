package majorfolio.backend.root.domain.assignment.repository;

import majorfolio.backend.root.domain.assignment.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    //각 기능을 하는 Query들을 작성해줘야 함
    List<Assignment> findTop5ByOrderByCreatedAtDesc();

    List<Assignment> findTop5ByOrderByLikesDesc();

    List<Assignment> findTop5ByOrderByUpdatedAtDesc();
}
