package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;
import majorfolio.backend.root.domain.material.entity.Material;

import java.time.LocalDateTime;

@Getter
@Builder
public class LibraryMaterialResponse {
    private final Long id;
    private final Long memberId;
    private final String imageUrl;
    private final String nickname;
    private final String className;
    private final String univ;
    private final String major;
    private final String semester;
    private final String professor;
    private final int like;
    private final LocalDateTime updateDate;
    private final Long buyInfoId;

    public static LibraryMaterialResponse of(Material material, LocalDateTime updateDate, Long buyInfoId) {
        return LibraryMaterialResponse.builder()
                .id(material.getId())
                .memberId(material.getMember().getId())
                .imageUrl(material.getMember().getProfileImage())
                .nickname(material.getMember().getNickName())
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
