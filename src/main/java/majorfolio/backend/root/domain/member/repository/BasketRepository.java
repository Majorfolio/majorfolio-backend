package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.member.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {
}
