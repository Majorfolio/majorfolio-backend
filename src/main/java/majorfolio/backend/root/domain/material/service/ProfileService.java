package majorfolio.backend.root.domain.material.service;

import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.material.dto.response.SellerProfileResponse;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final MemberRepository memberRepository;
    public SellerProfileResponse getSellerProfile(String nickName) {
        Member member = memberRepository.findByNickName(nickName);
        //아직 upload, sell, follower에 대한 기능 미구현으로 우선 0으로 대체후 나중에 구현 예정
        return SellerProfileResponse.of(member, 0, 0, 0);
    }
}
