/**
 * KakaoSocialLoginRepository
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.member.entity.KakaoSocialLogin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * KakaoSocialLogin 테이블의 Repository
 * @author 김영록
 * @version 0.0.1
 */
public interface KakaoSocialLoginRepository extends JpaRepository<KakaoSocialLogin, Long> {
    KakaoSocialLogin findByKakaoNumber(Long kakaoNumber);

}
