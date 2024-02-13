package majorfolio.backend.root.domain.payments.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 구매취소 성공 메세지 반환 형태 정의
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BuyCancelResponse {
    private String message;

    public static BuyCancelResponse of(String message){
        return BuyCancelResponse.builder()
                .message(message)
                .build();
    }
}
