package majorfolio.backend.root.domain.payments.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.payments.dto.request.CreateBuyInfoRequest;
import majorfolio.backend.root.domain.payments.dto.response.BuyCancelResponse;
import majorfolio.backend.root.domain.payments.dto.response.BuyInfoResponse;
import majorfolio.backend.root.domain.payments.dto.response.BuyMaterialListResponse;
import majorfolio.backend.root.domain.payments.dto.response.CreateBuyInfoResponse;
import majorfolio.backend.root.domain.payments.service.PaymentsService;
import org.springframework.web.bind.annotation.*;

/**
 * 결제에 관련된 컨트롤러 정의
 */
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentsController {
    private final PaymentsService paymentsService;

    /**
     * 결제 정보 페이지 요청
     * @return
     */
    @GetMapping("/coupon")
    public BuyMaterialListResponse getPaymentsList(HttpServletRequest request){
        return paymentsService.getPaymentsList(request);
    }

    /**
     * buyInfo 생성
     * @param createBuyInfoRequest
     * @param request
     * @return
     */
    @PostMapping("/info")
    public CreateBuyInfoResponse createBuyInfo(@RequestBody CreateBuyInfoRequest createBuyInfoRequest,
                                               HttpServletRequest request){
        return paymentsService.createBuyInfo(createBuyInfoRequest, request);
    }

    /**
     * buyInfo 정보 출력
     * @param buyInfoId
     * @return
     */
    @GetMapping("/info/{buyInfoId}")
    public BuyInfoResponse getBuyInfo(@PathVariable Long buyInfoId){
        return paymentsService.getBuyInfo(buyInfoId);
    }


    /**
     * 구매 취소애 대한 요청 처리
     * @param buyInfoId
     * @return
     */
    @PostMapping("/cancel/{buyInfoId}")
    public BuyCancelResponse doBuyCancel(@PathVariable Long buyInfoId){
        return paymentsService.doBuyCancel(buyInfoId);
    }
}
