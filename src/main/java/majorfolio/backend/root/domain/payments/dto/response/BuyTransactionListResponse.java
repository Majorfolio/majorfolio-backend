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
public class BuyTransactionListResponse {
    private int page;
    private List<TransactionResponse> beforePay;
    private List<TransactionResponse> beforeRefund;
    private List<TransactionResponse> afterPay;
    private List<TransactionResponse> isDown;
    private List<TransactionResponse> cancel;
    private List<TransactionResponse> afterRefund;

    public static BuyTransactionListResponse of(int page,
                                                List<TransactionResponse> beforePay,
                                                List<TransactionResponse> beforeRefund,
                                                List<TransactionResponse> afterPay,
                                                List<TransactionResponse> isDown,
                                                List<TransactionResponse> cancel,
                                                List<TransactionResponse> afterRefund){
        return BuyTransactionListResponse.builder()
                .page(page)
                .beforePay(beforePay)
                .beforeRefund(beforeRefund)
                .afterPay(afterPay)
                .isDown(isDown)
                .cancel(cancel)
                .afterRefund(afterRefund)
                .build();
    }
}
