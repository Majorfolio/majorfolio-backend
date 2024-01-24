package majorfolio.backend.root.global.provider;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;

import java.util.concurrent.TimeUnit;

public class CustomJwkProvider {
    private static final String KAKAO_URL = "https://kauth.kakao.com";
    private static volatile JwkProvider instance;

    private CustomJwkProvider() {
        // Private constructor to prevent instantiation.
    }

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
