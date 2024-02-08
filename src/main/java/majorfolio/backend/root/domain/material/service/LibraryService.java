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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LibraryService {
    private final KakaoSocialLoginRepository kakaoSocialLoginRepository;
    private final BuyListItemRepository buyListItemRepository;

    public LibraryMaterialListResponse getBuyMaterialList(HttpServletRequest request) {
        Object kakaoIdAttribute = request.getAttribute("kakaoId");

        if (kakaoIdAttribute == null) {
            // 카카오 아이디가 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException("카카오 아이디를 찾을 수 없습니다.");
        }

        Long kakaoId = Long.parseLong(kakaoIdAttribute.toString());

        // 카카오 아이디에 해당하는 값 조회
        KakaoSocialLogin kakaoSocialLogin = kakaoSocialLoginRepository.findById(kakaoId).orElse(null);
        if (kakaoSocialLogin == null || kakaoSocialLogin.getMember() == null || kakaoSocialLogin.getMember().getMajor1() == null) {
            // 카카오 아이디에 해당하는 값이 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException("카카오 아이디에 해당하는 정보를 찾을 수 없습니다.");
        }

        BuyList buyList = kakaoSocialLogin.getMember().getBuyList();

        List<BuyListItem> buyListItems = buyListItemRepository.findAllByBuyListOrderByBuyInfoCreatedAtDesc(buyList);


        List<BuyListItem> beforeBuyListItem = new ArrayList<>();
        List<BuyListItem> afterBuyListItem= new ArrayList<>();
        List<BuyListItem> downBuyListItem= new ArrayList<>();

        for(BuyListItem buyListItem : buyListItems){
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

    private List<LibraryMaterialResponse> convertToLibraryMaterialResponseListByBuyListItem(List<BuyListItem> buyListItems) {
        List<LibraryMaterialResponse> lists = new ArrayList<>();
        for(BuyListItem buyListItem : buyListItems)
            lists.add(LibraryMaterialResponse.of(buyListItem.getMaterial(), buyListItem.getBuyInfo().getUpdatedAt(),buyListItem.getBuyInfo().getId()));
        return lists;
    }

    private List<LibraryMaterialResponse> convertToDownLibraryMaterialResponseListByBuyListItem(List<BuyListItem> buyListItems) {
        List<LibraryMaterialResponse> lists = new ArrayList<>();
        for(BuyListItem buyListItem : buyListItems)
            lists.add(LibraryMaterialResponse.of(buyListItem.getMaterial(), buyListItem.getUpdatedAt(),buyListItem.getBuyInfo().getId()));
        return lists;
    }
}
