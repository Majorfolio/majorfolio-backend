package majorfolio.backend.root.domain.material.service;

import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.material.dto.response.SellerProfileResponse;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.repository.MemberRepository;
import majorfolio.backend.root.global.exception.NotFoundException;
import org.springframework.stereotype.Service;

/**
 * 프로필에 대한 기능을 수행하는 Service
 * @author 김태혁
 * @version 0.0.1
 */
@Service
@RequiredArgsConstructor
public class ProfileService {
    private final MemberRepository memberRepository;
    public SellerProfileResponse getSellerProfile(String nickName) {
        Member member = memberRepository.findByNickName(nickName);
        if (member == null) {
            // 해당 닉네임에 대한 멤버를 찾을 수 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException("해당 닉네임에 대한 멤버를 찾을 수 없습니다.");
        }
        //아직 upload, sell, follower에 대한 기능 미구현으로 우선 0으로 대체후 나중에 구현 예정
        return SellerProfileResponse.of(member, 0, 0, 0);
    }
}
