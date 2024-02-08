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
public class MyBookmark {
    private String nickName;
    private String profileUrl;
    private String className;
    private String university;
    private String major;
    private String type;
    private int totalRecommend;

    public static MyBookmark of(
            String nickName,
            String profileUrl,
            String className,
            String university,
            String major,
            String type,
            int totalRecommend
    ){
        return MyBookmark.builder()
                .nickName(nickName)
                .profileUrl(profileUrl)
                .className(className)
                .university(university)
                .major(major)
                .type(type)
                .totalRecommend(totalRecommend)
                .build();
    }
}
