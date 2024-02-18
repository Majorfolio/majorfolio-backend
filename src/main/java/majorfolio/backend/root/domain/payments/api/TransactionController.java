package majorfolio.backend.root.domain.payments.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.material.dto.response.BuyMaterialListResponse;
import majorfolio.backend.root.domain.payments.dto.response.BuyTransactionListResponse;
import majorfolio.backend.root.domain.payments.dto.response.SaleTransactionListResponse;
import majorfolio.backend.root.domain.payments.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/buy")
    public BuyTransactionListResponse getBuyTransactionList(@RequestParam(name = "page") int page,
                                                            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                            HttpServletRequest request){
        return transactionService.getBuyTransactionList(page, pageSize, request);
    }

    @GetMapping("/sale")
    public SaleTransactionListResponse getSaleTransactionList(@RequestParam(name = "page") int page,
                                                              @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                              HttpServletRequest request){
        return transactionService.getSaleTransactionList(page, pageSize, request);
    }
}
