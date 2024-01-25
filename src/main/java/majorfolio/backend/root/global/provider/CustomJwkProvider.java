/**
 * CustomJwkProvider
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.provider;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 공개키 프로바이더 클래스임
 * 카카오 서버로부터 요청을 보내면 공개키를 반환해줌(서명 검증 시 필요함)
 * 근데 서버로부터 빈번한 요청이 오면 요청이 차단되어서 싱글톤 객체로 구현함
 * (불필요한 요청을 막기 위함)
 *
 * @author 김영록
 * @version 0.0.1
 */
public class CustomJwkProvider {
    private static final String KAKAO_URL = "https://kauth.kakao.com";
    private static volatile JwkProvider instance;

    private CustomJwkProvider() {
        // Private constructor to prevent instantiation.
    }

    /**
     * 공개키를 받아오는 로직메소드임
     * @return
     */
    public static JwkProvider getInstance() {
        if (instance == null) {
            synchronized (CustomJwkProvider.class) {
                if (instance == null) {
                    instance = new JwkProviderBuilder(KAKAO_URL)
                            .cached(10, 7, TimeUnit.DAYS)
                            .build();
                }
            }
        }
        return instance;
    }
}
