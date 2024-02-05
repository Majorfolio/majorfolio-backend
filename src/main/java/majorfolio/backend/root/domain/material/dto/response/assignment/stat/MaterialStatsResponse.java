/**
 * MaterialStatsResponse
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
 * 과제 통계 API의 응답 객체 부분
 *
 * @author 김영록
 * @version 0.0.1
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MaterialStatsResponse {

    private SaleStat saleStat;
    private ViewStat viewStat;
    private BookmarkStat bookmarkStat;

    public static MaterialStatsResponse of(
            SaleStat saleStat,
            ViewStat viewStat,
            BookmarkStat bookmarkStat
    ){
        return MaterialStatsResponse.builder()
                .saleStat(saleStat)
                .viewStat(viewStat)
                .bookmarkStat(bookmarkStat)
                .build();
    }

}
