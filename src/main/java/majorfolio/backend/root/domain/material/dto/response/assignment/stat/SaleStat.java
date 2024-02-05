/**
 * SaleStat
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
 * MaterialStatsReponse의 멤버 변수중 판매 통계 부분
 *
 * @author 김영록
 * @version 0.0.1
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaleStat {
    private Long totalSale;
    private Long weeklySale;
    private Long todaySale;

    public static SaleStat of(
            Long totalSale,
            Long weeklySale,
            Long todaySale
    ){
        return SaleStat.builder()
                .totalSale(totalSale)
                .weeklySale(weeklySale)
                .todaySale(todaySale)
                .build();
    }
}
