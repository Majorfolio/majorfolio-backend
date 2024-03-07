package majorfolio.backend.root.domain.payments.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SaleTransactionListResponse {
    private int page;
    private List<TransactionResponse> pending;
    private List<TransactionResponse> complete;
    private boolean isEnd;

    public static SaleTransactionListResponse of(int page,
                                                 List<TransactionResponse> pending,
                                                 List<TransactionResponse> complete,
                                                 boolean isEnd){
        return SaleTransactionListResponse.builder()
                .page(page)
                .pending(pending)
                .complete(complete)
                .isEnd(isEnd)
                .build();
    }
}
