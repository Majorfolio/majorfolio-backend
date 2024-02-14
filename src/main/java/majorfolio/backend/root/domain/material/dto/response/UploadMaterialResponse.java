package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;
import majorfolio.backend.root.domain.material.entity.Material;

import java.time.LocalDateTime;

/**
 * 업로드한 자료들의 반환 형태
 */
@Getter
@Builder
public class UploadMaterialResponse {
    private final Long id;
    private final String imageUrl;
    private final String nickname;
    private final String className;
    private final String univ;
    private final String major;
    private final String semester;
    private final String professor;
    private final int like;
    private final LocalDateTime updateDate;

    public static UploadMaterialResponse of(Material material) {
        return UploadMaterialResponse.builder()
                .id(material.getId())
                .imageUrl(material.getMember().getProfileImage())
                .nickname(material.getMember().getNickName())
                .className(material.getClassName())
                .univ(material.getMember().getUniversityName())
                .major(material.getMajor())
                .semester(material.getSemester())
                .professor(material.getProfessor())
                .like(material.getTotalRecommend())
                .updateDate(material.getUpdatedAt())
                .build();
    }
}
