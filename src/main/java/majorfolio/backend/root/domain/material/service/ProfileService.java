package majorfolio.backend.root.domain.material.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.material.dto.response.ProfileResponse;
import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.material.repository.MaterialRepository;
import majorfolio.backend.root.domain.member.entity.KakaoSocialLogin;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.repository.FollowerRepository;
import majorfolio.backend.root.domain.member.repository.KakaoSocialLoginRepository;
import majorfolio.backend.root.domain.member.repository.MemberRepository;
import majorfolio.backend.root.domain.member.repository.SellListItemRepository;
import majorfolio.backend.root.global.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.*;

/**
 * 프로필에 대한 기능을 수행하는 Service
 * @author 김태혁
 * @version 0.0.1
 */
@Service
@RequiredArgsConstructor
public class ProfileService {
    private final MemberRepository memberRepository;
    private final MaterialRepository materialRepository;
    private final SellListItemRepository sellListItemRepository;
    private final FollowerRepository followerRepository;
    private final KakaoSocialLoginRepository kakaoSocialLoginRepository;

    public ProfileResponse getProfile(Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null) {
            // 해당 닉네임에 대한 멤버를 찾을 수 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_MEMBER_FROM_NICKNAME);
        }
        Long uploadSize = 0L;
        Long totalSale = 0L;

        List<Material> uploadList = materialRepository.findAllByMemberAndStatus(member, "active");
        uploadSize = Integer.toUnsignedLong(uploadList.size());
        for(Material m : uploadList)
            totalSale += sellListItemRepository.countByMaterialIdAndStatus(m.getId(),"complete");
        Long totalFollower = followerRepository.countByMemberAndStatus(member.getId(),true);

        return ProfileResponse.of(member, uploadSize, totalSale, totalFollower);
    }

    /**
     * request로 유저의 accessToken이 들어왔을 때 해당유저의 마이페이지에 정보를 전달하기 위해
     * 응답 형태를 반환하는 메서드
     * @param request
     * @return
     */
    public ProfileResponse getUserProfile(HttpServletRequest request) {
        Object kakaoIdAttribute = request.getAttribute("kakaoId");

        if (kakaoIdAttribute == null) {
            // 카카오 아이디가 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_KAKAOID);
        }

        Long kakaoId = Long.parseLong(kakaoIdAttribute.toString());

        // 카카오 아이디에 해당하는 값 조회
        KakaoSocialLogin kakaoSocialLogin = kakaoSocialLoginRepository.findById(kakaoId).orElse(null);
        if (kakaoSocialLogin == null || kakaoSocialLogin.getMember() == null ) {
            throw new NotFoundException(NOT_FOUND_INFO_FROM_KAKAOID);
        }

        Long memberId = kakaoSocialLogin.getMember().getId();
        return getProfile(memberId);
    }
}
