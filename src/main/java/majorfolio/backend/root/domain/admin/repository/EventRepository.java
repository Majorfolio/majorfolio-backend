package majorfolio.backend.root.domain.admin.repository;

import majorfolio.backend.root.domain.admin.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

}
