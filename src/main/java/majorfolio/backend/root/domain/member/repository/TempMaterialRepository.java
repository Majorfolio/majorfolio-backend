package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.member.entity.TempMaterial;
import majorfolio.backend.root.domain.member.entity.TempStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TempMaterialRepository extends JpaRepository<TempMaterial, Long> {
    Page<TempMaterial> findAllByTempStorage(TempStorage tempStorage, Pageable pageable);
}
