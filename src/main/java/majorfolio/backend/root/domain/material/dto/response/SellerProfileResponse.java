package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;
import majorfolio.backend.root.domain.member.entity.Member;

/**
 * 판매자의 프로필 응답 형태 정의
 * @author 김태혁
 * @version 0.0.1
 */
@Getter
@Builder
public class SellerProfileResponse {
    private String nickName;
    private String univName;
    private String major;
    private String image_url;
    private int upload;
    private int sell;
    private int follower;

    public static SellerProfileResponse of(Member member, int upload, int sell, int follower){
        return SellerProfileResponse.builder()
                .nickName(member.getNickName())
                .univName(member.getUniversityName())
                .major(member.getMajor1())
                .image_url(member.getProfileImage())
                .upload(upload)
                .sell(sell)
                .follower(follower)
                .build();
    }
}
