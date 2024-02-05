/**
 * BookmarkStat
 *
 * 2024.02.05
 *
 * 0.0.1
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.material.dto.response.assignment.stat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * MaterialStatsResponse의 멤버 변수중 북마크 통계 부분
 *
 * @author 김영록
 * @version 0.0.1
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkStat {
    private Long totalBookmark;
    private Long weeklyBookmark;
    private Long todayBookmark;

    public static BookmarkStat of(
            Long totalBookmark,
            Long weeklyBookmark,
            Long todayBookmark
    ){
        return BookmarkStat.builder()
                .totalBookmark(totalBookmark)
                .weeklyBookmark(weeklyBookmark)
                .todayBookmark(todayBookmark)
                .build();
    }
}
