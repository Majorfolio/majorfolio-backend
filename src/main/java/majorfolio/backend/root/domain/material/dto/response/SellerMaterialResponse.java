package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;
import majorfolio.backend.root.domain.material.entity.Material;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 판매자의 material의 응답 형태 정의
 * @author 김태혁
 * @version 0.0.1
 */
@Getter
@Builder
public class SellerMaterialResponse {
    private final Long id;
    private final Long memberId;
    private final String imageUrl;
    private final String nickName;
    private final String title;
    private final String univ;
    private final String major;
    private final String semester;
    private final String professor;
    private final int like;
    private final LocalDateTime createdAt;

    /**
     * material의 응답 형태 생성
     * @author 김태혁
     * @version 0.0.1
     */
    public static SellerMaterialResponse of(Material material) {
        return SellerMaterialResponse.builder()
                .id(material.getId())
                .memberId(material.getMember().getId())
                .imageUrl(material.getMember().getProfileImage())
                .nickName(material.getMember().getNickName())
                .title(material.getName())
                .univ(material.getMember().getUniversityName())
                .major(material.getMajor())
                .semester(material.getSemester())
                .professor(material.getProfessor())
                .like(material.getTotalRecommend())
                .createdAt(material.getCreatedAt())
                .build();
    }
}
