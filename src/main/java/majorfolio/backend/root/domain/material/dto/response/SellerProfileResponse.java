package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;
import majorfolio.backend.root.domain.member.entity.Member;

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
