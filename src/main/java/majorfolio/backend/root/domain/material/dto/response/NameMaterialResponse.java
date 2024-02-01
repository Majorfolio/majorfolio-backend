package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;
import majorfolio.backend.root.domain.material.entity.Material;

/**
 * material의 응답 형태 정의
 * @author 김태혁
 * @version 0.0.1
 */
@Getter
@Builder
public class NameMaterialResponse {
    private final Long id;
    private final String nickname;
    private final String name;
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
    public static NameMaterialResponse of(Material material) {
        return NameMaterialResponse.builder()
                .id(material.getId())
                .nickname(material.getMember().getNickName())
                .name(material.getName())
                .univ(material.getMember().getUniversityName())
                .major(material.getMajor())
                .semester(material.getSemester())
                .professor(material.getProfessor())
                .like(material.getTotalRecommend())
                .build();
    }
}