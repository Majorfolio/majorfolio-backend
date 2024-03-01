package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.member.entity.TempStorage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempStorageRepository extends JpaRepository<TempStorage, Long> {

}
