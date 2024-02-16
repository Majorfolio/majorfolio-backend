package majorfolio.backend.root.domain.material.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.material.dto.response.*;
import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.material.repository.MaterialRepository;
import majorfolio.backend.root.domain.member.entity.BuyList;
import majorfolio.backend.root.domain.member.entity.BuyListItem;
import majorfolio.backend.root.domain.member.entity.KakaoSocialLogin;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.repository.BuyListItemRepository;
import majorfolio.backend.root.domain.member.service.MemberGlobalService;
import majorfolio.backend.root.global.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.*;

/**
 * 자료함에 대한 기능을 작성한 Service
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LibraryService {
    private final MaterialRepository materialRepository;
    private final BuyListItemRepository buyListItemRepository;
    private final MemberGlobalService memberGlobalService;

    /**
     * 토큰에 해당하는 아이디와 page 정보에 따라 구매한 자료들을 전달하는 메서드
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    public BuyMaterialListResponse getBuyMaterialList(int page, int pageSize, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
//        Object kakaoIdAttribute = request.getAttribute("kakaoId");
//
//        if (kakaoIdAttribute == null) {
//            // 카카오 아이디가 없을 때의 예외 처리 또는 메시지 전달 등의 처리
//            throw new NotFoundException(NOT_FOUND_KAKAOID);
//        }
//
//        Long kakaoId = Long.parseLong(kakaoIdAttribute.toString());
//
//        // 카카오 아이디에 해당하는 값 조회
//        KakaoSocialLogin kakaoSocialLogin = kakaoSocialLoginRepository.findById(kakaoId).orElse(null);
//        if (kakaoSocialLogin == null || kakaoSocialLogin.getMember() == null || kakaoSocialLogin.getMember().getMajor1() == null) {
//            // 카카오 아이디에 해당하는 값이 없을 때의 예외 처리 또는 메시지 전달 등의 처리
//            throw new NotFoundException(NOT_FOUND_INFO_FROM_KAKAOID);
//        }
        KakaoSocialLogin kakaoSocialLogin = memberGlobalService.getMemberByToken(request);

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

        List<BuyMaterialResponse> beforeList = convertToLibraryMaterialResponseListByBuyListItem(beforeBuyListItem);
        List<BuyMaterialResponse> afterList = convertToLibraryMaterialResponseListByBuyListItem(afterBuyListItem);
        List<BuyMaterialResponse> downList = convertToDownLibraryMaterialResponseListByBuyListItem(downBuyListItem);

        return BuyMaterialListResponse.of(beforeList, afterList, downList);
    }

    /**
     * 결제한 날짜 포함하여 구매 정보 리스트로 반환하는 메서드
     * @param buyListItems
     * @return
     */
    private List<BuyMaterialResponse> convertToLibraryMaterialResponseListByBuyListItem(List<BuyListItem> buyListItems) {
        List<BuyMaterialResponse> lists = new ArrayList<>();
        for(BuyListItem buyListItem : buyListItems)
            lists.add(BuyMaterialResponse.of(buyListItem.getMaterial(), buyListItem.getBuyInfo().getUpdatedAt(),buyListItem.getBuyInfo().getId()));
        return lists;
    }

    /**
     * 다운로드한 날짜를 포함하여 구매 정보 리스트로 반환하는 메서드
     * @param buyListItems
     * @return
     */
    private List<BuyMaterialResponse> convertToDownLibraryMaterialResponseListByBuyListItem(List<BuyListItem> buyListItems) {
        List<BuyMaterialResponse> lists = new ArrayList<>();
        for(BuyListItem buyListItem : buyListItems)
            lists.add(BuyMaterialResponse.of(buyListItem.getMaterial(), buyListItem.getUpdatedAt(),buyListItem.getBuyInfo().getId()));
        return lists;
    }

    /**
     * 유저가 업로드한 과제의 상태에 따라 전달
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    public UploadMaterialListResponse getUploadMaterialList(int page, int pageSize, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        KakaoSocialLogin kakaoSocialLogin = memberGlobalService.getMemberByToken(request);
        Member member = kakaoSocialLogin.getMember();

        Page<Material> materialList = materialRepository.findAllByMemberOrderByUpdatedAtDesc(member, pageable);

        List<Material> activeMaterials = new ArrayList<>();
        List<Material> stopMaterials = new ArrayList<>();

        for(Material m : materialList){
            if(m.getStatus().equals("active"))
                activeMaterials.add(m);
            else
                stopMaterials.add(m);
        }

        List<UploadMaterialResponse> activeMaterialResponseList = convertToUploadMaterialResponseListByMaterial(activeMaterials);
        List<UploadMaterialResponse> stopMaterialResponseList = convertToUploadMaterialResponseListByMaterial(stopMaterials);

        return UploadMaterialListResponse.of(activeMaterialResponseList, stopMaterialResponseList);
    }

    /**
     * material list를 uploadMaterialResponse list로 변환
     * @param materials
     * @return
     */
    private List<UploadMaterialResponse> convertToUploadMaterialResponseListByMaterial(List<Material> materials) {
        return materials.stream()
                .map(UploadMaterialResponse::of)
                .collect(Collectors.toList());
    }
}
