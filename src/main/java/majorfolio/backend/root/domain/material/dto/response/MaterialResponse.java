package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;
import majorfolio.backend.root.domain.material.entity.Material;

/**
 * home 에서의 material의 응답 형태 정의
 * @author 김태혁
 * @version 0.0.1
 */
@Getter
@Builder
public class MaterialResponse {
    private final Long id;
    private final Long memberId;
    private final String profileImage;
    private final String nickName;
    private final String className;
    private final String univ;
    private final String major;
    private final String semester;
    private final String professor;
    private final int like;

    /**
     * material의 응답 형태 생성
     * @author 김태혁
     * @version 0.0.1
     */
    public static MaterialResponse of(Material material) {
        return MaterialResponse.builder()
                .id(material.getId())
                .memberId(material.getMember().getId())
                .profileImage(material.getMember().getProfileImage())
                .nickName(material.getMember().getNickName())
                .className(material.getClassName())
                .univ(material.getMember().getUniversityName())
                .major(material.getMajor())
                .semester(material.getSemester())
                .professor(material.getProfessor())
                .like(material.getTotalRecommend())
                .build();
    }
}
