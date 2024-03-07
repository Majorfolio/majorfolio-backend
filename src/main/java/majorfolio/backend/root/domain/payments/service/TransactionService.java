package majorfolio.backend.root.domain.payments.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.material.dto.response.BuyMaterialResponse;
import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.material.repository.MaterialRepository;
import majorfolio.backend.root.domain.member.entity.*;
import majorfolio.backend.root.domain.member.repository.BuyListItemRepository;
import majorfolio.backend.root.domain.member.repository.SellListItemRepository;
import majorfolio.backend.root.domain.member.service.MemberGlobalService;
import majorfolio.backend.root.domain.payments.dto.response.BuyTransactionListResponse;
import majorfolio.backend.root.domain.payments.dto.response.SaleTransactionListResponse;
import majorfolio.backend.root.domain.payments.dto.response.TransactionResponse;
import majorfolio.backend.root.global.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.NOT_FOUND_MATERIAL;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final MemberGlobalService memberGlobalService;
    private final BuyListItemRepository buyListItemRepository;
    private final SellListItemRepository sellListItemRepository;
    private final MaterialRepository materialRepository;
    public BuyTransactionListResponse getBuyTransactionList(int page, int pageSize, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        KakaoSocialLogin kakaoSocialLogin = memberGlobalService.getMemberByToken(request);

        BuyList buyList = kakaoSocialLogin.getMember().getBuyList();

        Page<BuyListItem> pagedBuyListItems = buyListItemRepository.findAllByBuyListOrderByBuyInfoUpdatedAtDesc(buyList, pageable);

        if (pagedBuyListItems == null || pagedBuyListItems.isEmpty()) {
            // 더 이상 자료가 없습니다. 예외 발생 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_MATERIAL);
        }

        List<BuyListItem> beforeBuyListItem = new ArrayList<>();
        List<BuyListItem> afterBuyListItem= new ArrayList<>();
        List<BuyListItem> downBuyListItem= new ArrayList<>();
        List<BuyListItem> beforeRefundListItem = new ArrayList<>();
        List<BuyListItem> afterRefundListItem= new ArrayList<>();
        List<BuyListItem> cancelListItem= new ArrayList<>();

        for(BuyListItem buyListItem : pagedBuyListItems){
            switch (buyListItem.getBuyInfo().getStatus()) {
                case "beforePay" -> beforeBuyListItem.add(buyListItem);
                case "cancel" -> cancelListItem.add(buyListItem);
                case "beforeRefund" -> beforeRefundListItem.add(buyListItem);
                case "afterRefund" -> afterRefundListItem.add(buyListItem);
                default -> {
                    if (!buyListItem.getIsDown())
                        afterBuyListItem.add(buyListItem);
                    else
                        downBuyListItem.add(buyListItem);
                }
            }
        }

        List<TransactionResponse> beforeBuyList = convertToTransactionResponseListByBuyListItem(beforeBuyListItem);
        List<TransactionResponse> afterBuyList = convertToTransactionResponseListByBuyListItem(afterBuyListItem);
        List<TransactionResponse> downBuyList = convertToDownTransactionResponseListByBuyListItem(downBuyListItem);
        List<TransactionResponse> beforeRefundList = convertToTransactionResponseListByBuyListItem(beforeRefundListItem);
        List<TransactionResponse> afterRefundList = convertToTransactionResponseListByBuyListItem(afterRefundListItem);
        List<TransactionResponse> cancelList = convertToTransactionResponseListByBuyListItem(cancelListItem);

        int totalPages = pagedBuyListItems.getTotalPages();
        boolean isLastPage = page >= totalPages;

        return BuyTransactionListResponse.of(page, beforeBuyList, beforeRefundList, afterBuyList, downBuyList, cancelList, afterRefundList, isLastPage);
    }

    private List<TransactionResponse> convertToTransactionResponseListByBuyListItem(List<BuyListItem> buyListItems) {
        List<TransactionResponse> lists = new ArrayList<>();
        for(BuyListItem buyListItem : buyListItems)
            lists.add(TransactionResponse.of(buyListItem.getMaterial(), buyListItem.getBuyInfo().getUpdatedAt()));
        return lists;
    }

    private List<TransactionResponse> convertToDownTransactionResponseListByBuyListItem(List<BuyListItem> buyListItems) {
        List<TransactionResponse> lists = new ArrayList<>();
        for(BuyListItem buyListItem : buyListItems)
            lists.add(TransactionResponse.of(buyListItem.getMaterial(), buyListItem.getUpdatedAt()));
        return lists;
    }

    public SaleTransactionListResponse getSaleTransactionList(int page, int pageSize, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        KakaoSocialLogin kakaoSocialLogin = memberGlobalService.getMemberByToken(request);
        Member member = kakaoSocialLogin.getMember();

        Page<SellListItem> sellListItems = sellListItemRepository.findAllBySellListOrderByUpdatedAtDesc(member.getSellList(), pageable);

        List<Material> pendingMaterials = new ArrayList<>();
        List<Material> completeMaterials = new ArrayList<>();

        for(SellListItem sellListItem : sellListItems){
            Material material = materialRepository.findById(sellListItem.getMaterialId()).orElse(null);
            if(sellListItem.getStatus().equals("payComplete"))
                pendingMaterials.add(material);
            else if(sellListItem.getStatus().equals("buyComplete"))
                completeMaterials.add(material);
        }

        List<TransactionResponse> pendingTransactionResponseList = convertToTransactionSaleResponseListByMaterial(pendingMaterials);
        List<TransactionResponse> completeTransactionResponseList = convertToTransactionSaleResponseListByMaterial(completeMaterials);

        int totalPages = sellListItems.getTotalPages();
        boolean isLastPage = page >= totalPages;

        return SaleTransactionListResponse.of(page, pendingTransactionResponseList, completeTransactionResponseList, isLastPage);
    }

    private List<TransactionResponse> convertToTransactionSaleResponseListByMaterial(List<Material> materials) {
        List<TransactionResponse> lists = new ArrayList<>();
        for(Material material : materials)
            lists.add(TransactionResponse.of(material, material.getUpdatedAt()));
        return lists;
    }
}
