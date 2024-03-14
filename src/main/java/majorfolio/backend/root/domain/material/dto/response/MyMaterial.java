/**
 * MyBookmark
 *
 * 2024.02.08
 *
 * 0.0.1
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * BookmarkListResponse에서 myBookmarkList의 원소부분
 *
 * @author 김영록
 * @version 0.0.1
 */
@Getter
@Builder
public class MyMaterial {
    private Long materialId;
    private Long memberId;
    private String nickName;
    private String profileUrl;
    private String className;
    private String univ;
    private String major;
    private String type;
    private int totalRecommend;

    public static MyMaterial of(
            Long materialId,
            Long memberId,
            String nickName,
            String profileUrl,
            String className,
            String university,
            String major,
            String type,
            int totalRecommend
    ){
        return MyMaterial.builder()
                .materialId(materialId)
                .memberId(memberId)
                .nickName(nickName)
                .profileUrl(profileUrl)
                .className(className)
                .univ(university)
                .major(major)
                .type(type)
                .totalRecommend(totalRecommend)
                .build();
    }
}
