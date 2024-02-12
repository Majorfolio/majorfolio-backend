package majorfolio.backend.root.domain.material.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.material.dto.response.LibraryMaterialListResponse;
import majorfolio.backend.root.domain.material.dto.response.LibraryMaterialResponse;
import majorfolio.backend.root.domain.member.entity.BuyList;
import majorfolio.backend.root.domain.member.entity.BuyListItem;
import majorfolio.backend.root.domain.member.entity.KakaoSocialLogin;
import majorfolio.backend.root.domain.member.repository.BuyListItemRepository;
import majorfolio.backend.root.domain.member.repository.KakaoSocialLoginRepository;
import majorfolio.backend.root.global.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.*;

/**
 * 자료함에 대한 기능을 작성한 Service
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LibraryService {
    private final KakaoSocialLoginRepository kakaoSocialLoginRepository;
    private final BuyListItemRepository buyListItemRepository;

    /**
     * 토큰에 해당하는 아이디와 page 정보에 따라 구매한 자료들을 전달하는 메서드
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    public LibraryMaterialListResponse getBuyMaterialList(int page, int pageSize, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Object kakaoIdAttribute = request.getAttribute("kakaoId");

        if (kakaoIdAttribute == null) {
            // 카카오 아이디가 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_KAKAOID);
        }

        Long kakaoId = Long.parseLong(kakaoIdAttribute.toString());

        // 카카오 아이디에 해당하는 값 조회
        KakaoSocialLogin kakaoSocialLogin = kakaoSocialLoginRepository.findById(kakaoId).orElse(null);
        if (kakaoSocialLogin == null || kakaoSocialLogin.getMember() == null || kakaoSocialLogin.getMember().getMajor1() == null) {
            // 카카오 아이디에 해당하는 값이 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_INFO_FROM_KAKAOID);
        }

        BuyList buyList = kakaoSocialLogin.getMember().getBuyList();

        Page<BuyListItem> pagedBuyListItems = buyListItemRepository.findAllByBuyListOrderByBuyInfoCreatedAtDesc(buyList, pageable);

        if (pagedBuyListItems == null || pagedBuyListItems.isEmpty()) {
            // 더 이상 자료가 없습니다. 예외 발생 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_MATERIAL);
        }

        List<BuyListItem> beforeBuyListItem = new ArrayList<>();
        List<BuyListItem> afterBuyListItem= new ArrayList<>();
        List<BuyListItem> downBuyListItem= new ArrayList<>();

        for(BuyListItem buyListItem : pagedBuyListItems){
            if(!buyListItem.getBuyInfo().getIsPay())
                beforeBuyListItem.add(buyListItem);
            else{
                if(!buyListItem.getIsDown())
                    afterBuyListItem.add(buyListItem);
                else
                    downBuyListItem.add(buyListItem);
            }
        }

        List<LibraryMaterialResponse> beforeList = convertToLibraryMaterialResponseListByBuyListItem(beforeBuyListItem);
        List<LibraryMaterialResponse> afterList = convertToLibraryMaterialResponseListByBuyListItem(afterBuyListItem);
        List<LibraryMaterialResponse> downList = convertToDownLibraryMaterialResponseListByBuyListItem(downBuyListItem);

        return LibraryMaterialListResponse.of(beforeList, afterList, downList);
    }

    /**
     * 결제한 날짜 포함하여 구매 정보 리스트로 반환하는 메서드
     * @param buyListItems
     * @return
     */
    private List<LibraryMaterialResponse> convertToLibraryMaterialResponseListByBuyListItem(List<BuyListItem> buyListItems) {
        List<LibraryMaterialResponse> lists = new ArrayList<>();
        for(BuyListItem buyListItem : buyListItems)
            lists.add(LibraryMaterialResponse.of(buyListItem.getMaterial(), buyListItem.getBuyInfo().getUpdatedAt(),buyListItem.getBuyInfo().getId()));
        return lists;
    }

    /**
     * 다운로드한 날짜를 포함하여 구매 정보 리스트로 반환하는 메서드
     * @param buyListItems
     * @return
     */
    private List<LibraryMaterialResponse> convertToDownLibraryMaterialResponseListByBuyListItem(List<BuyListItem> buyListItems) {
        List<LibraryMaterialResponse> lists = new ArrayList<>();
        for(BuyListItem buyListItem : buyListItems)
            lists.add(LibraryMaterialResponse.of(buyListItem.getMaterial(), buyListItem.getUpdatedAt(),buyListItem.getBuyInfo().getId()));
        return lists;
    }
}
