package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;
import majorfolio.backend.root.domain.material.entity.Material;

import java.time.LocalDateTime;

/**
 * 구매한 자료들의 반환 형태
 */
@Getter
@Builder
public class BuyMaterialResponse {
    private final Long id;
    private final Long memberId;
    private final String imageUrl;
    private final String nickName;
    private final String className;
    private final String univ;
    private final String major;
    private final String semester;
    private final String professor;
    private final int like;
    private final LocalDateTime updateDate;
    private final Long buyInfoId;

    public static BuyMaterialResponse of(Material material, LocalDateTime updateDate, Long buyInfoId) {
        return BuyMaterialResponse.builder()
                .id(material.getId())
                .memberId(material.getMember().getId())
                .imageUrl(material.getMember().getProfileImage())
                .nickName(material.getMember().getNickName())
                .className(material.getClassName())
                .univ(material.getMember().getUniversityName())
                .major(material.getMajor())
                .semester(material.getSemester())
                .professor(material.getProfessor())
                .like(material.getTotalRecommend())
                .updateDate(updateDate)
                .buyInfoId(buyInfoId)
                .build();
    }
}
