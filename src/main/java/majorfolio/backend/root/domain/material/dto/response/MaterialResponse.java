package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;
import majorfolio.backend.root.domain.material.entity.Material;

@Getter
@Builder
public class MaterialResponse {
    private final Long id;
    private final String nickname;
    private final String subjectName;
    private final String univ;
    private final String major;
    private final int like;

    public static MaterialResponse of(Material material) {
        return MaterialResponse.builder()
                .id(material.getId())
                .nickname(material.getMember().getNickName())
                .subjectName(material.getName())
                .univ(material.getMember().getUniversityName())
                .major(material.getMajor())
                .like(material.getTotalRecommend())
                .build();
    }
}
