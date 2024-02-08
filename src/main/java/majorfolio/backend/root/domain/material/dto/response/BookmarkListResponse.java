/**
 * BookmarkListReponse
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

import java.util.List;

/**
 * 마이페이지 -> 북마크한 자료 모아보기 api에서 Response객체 부분
 *
 * @author 김영록
 * @version 0.0.1
 */
@Getter
@Builder
public class BookmarkListResponse {
    private int page;
    private List<MyBookmark> myBookmarkList;

    public static BookmarkListResponse of(int page, List<MyBookmark> myBookmarkList){
        return BookmarkListResponse.builder()
                .page(page)
                .myBookmarkList(myBookmarkList)
                .build();
    }

}
