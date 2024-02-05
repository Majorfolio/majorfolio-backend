package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.member.entity.Likes;
import majorfolio.backend.root.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Likes findByMemberAndMaterial(Member member, Material material);
}
