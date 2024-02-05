/**
 * ViewStat
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
 * MaterialStatsResponse의 멤버 변수 중 조회 통계 부분
 *
 * @author 김영록
 * @version 0.0.1
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ViewStat {
    private Long totalView;
    private Long weeklyView;
    private Long todayView;

    public static ViewStat of(
            Long totalView,
            Long weeklyView,
            Long todayView
    ){
        return ViewStat.builder()
                .totalView(totalView)
                .weeklyView(weeklyView)
                .todayView(todayView)
                .build();
    }
}
