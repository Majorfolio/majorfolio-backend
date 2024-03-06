package majorfolio.backend.root.global.argument_resolver;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.member.entity.EmailDB;
import majorfolio.backend.root.domain.member.entity.KakaoSocialLogin;
import majorfolio.backend.root.domain.member.entity.Member;

@Getter
@Builder
@RequiredArgsConstructor
public class TokenInformation {
    private KakaoSocialLogin kakaoSocialLogin;
    private EmailDB emailDB;
    private Member member;

    public static TokenInformation of(KakaoSocialLogin kakaoSocialLogin,
                                   EmailDB emailDB, Member member){
        return TokenInformation.builder()
                .kakaoSocialLogin(kakaoSocialLogin)
                .emailDB(emailDB)
                .member(member)
                .build();
    }
}
