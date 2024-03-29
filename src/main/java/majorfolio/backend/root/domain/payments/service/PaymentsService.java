package majorfolio.backend.root.domain.payments.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.material.repository.MaterialRepository;
import majorfolio.backend.root.domain.member.entity.BuyListItem;
import majorfolio.backend.root.domain.member.entity.KakaoSocialLogin;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.entity.SellListItem;
import majorfolio.backend.root.domain.member.repository.BuyListItemRepository;
import majorfolio.backend.root.domain.member.repository.SellListItemRepository;
import majorfolio.backend.root.domain.member.service.MemberGlobalService;
import majorfolio.backend.root.domain.payments.dto.request.CouponIdRequest;
import majorfolio.backend.root.domain.payments.dto.request.CreateBuyInfoRequest;
import majorfolio.backend.root.domain.payments.dto.request.MaterialIdRequest;
import majorfolio.backend.root.domain.payments.dto.request.RefundRequest;
import majorfolio.backend.root.domain.payments.dto.response.*;
import majorfolio.backend.root.domain.payments.entity.BuyInfo;
import majorfolio.backend.root.domain.payments.entity.RefundInfo;
import majorfolio.backend.root.domain.payments.repository.BuyInfoRepository;
import majorfolio.backend.root.domain.payments.repository.RefundRepository;
import majorfolio.backend.root.global.exception.CancelAfterPayException;
import majorfolio.backend.root.global.exception.NotFoundException;
import majorfolio.backend.root.global.exception.PaymentsException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.*;

/**
 * 결제와 관련된 기능을 처리하는 service
 */
@Service
@RequiredArgsConstructor
public class PaymentsService {
    private final MemberGlobalService memberGlobalService;
    private final MaterialRepository materialRepository;
    private final BuyInfoRepository buyInfoRepository;
    private final BuyListItemRepository buyListItemRepository;
    private final SellListItemRepository sellListItemRepository;
    private final RefundRepository refundRepository;

    private final SecureRandom random = new SecureRandom();

    /**
     * 결제화면에 가격 및 쿠폰 정보 전달
     * @return
     */
    public BuyMaterialListResponse getPaymentsList(HttpServletRequest request) {
        //KakaoSocialLogin kakaoSocialLogin = memberGlobalService.getMemberByToken(request);

        //현재는 쿠폰 미구현으로 아직 그냥 0, 0으로 전달할거지만 후에 쿠폰을 구현하게 된다면, 쿠폰함에서 사용가능한 쿠폰의 개수로 전달홰야 함
        return BuyMaterialListResponse.of(0, 0);
    }

    /**
     * 구매 요청시 buyInfo 생성 및 구매자 판매자 처리
     * @param createBuyInfoRequest
     * @param request
     * @return
     */
    public CreateBuyInfoResponse createBuyInfo(CreateBuyInfoRequest createBuyInfoRequest, HttpServletRequest request) {
        KakaoSocialLogin kakaoSocialLogin = memberGlobalService.getMemberByToken(request);
        int price = createBuyInfoRequest.getTotalPrice();
        List<MaterialIdRequest> materialIdRequests = createBuyInfoRequest.getAssignmentIdList();
        //현재는 쿠폰 기능 미구현으로 아직 쿠폰 사용에 대한 처리는 X
        List<CouponIdRequest> couponIdRequests = createBuyInfoRequest.getCouponIdList();
        Member member = kakaoSocialLogin.getMember();
        //code 생성
        String code = getRandomCode();

        //buyInfo를 생성
        BuyInfo buyInfo = BuyInfo.of(code, price, member.getId(), "beforePay");
        buyInfoRepository.save(buyInfo);

        //구매자 플로우
        //buyList 가져와서 과제들을 buyListItem으로 만들고
        //그거를 BuyInfo에 넣기
        for(MaterialIdRequest id : materialIdRequests){
            Material material = materialRepository.findById(id.getAssignmentId()).orElse(null);
            // 구매자 입장에서 구매 아이템 추가 및 BuyInfo에 자료 아이디 리스트에 추가
            BuyListItem buyListItem = BuyListItem.of(material, member.getBuyList());
            buyInfo.addBuyListItem(buyListItem);
            buyListItemRepository.save(buyListItem);

            //판매자 입장에서 판매리스트에 추가
            Member seller = material.getMember();
            SellListItem sellListItem = SellListItem.of(buyInfo.getCreatedAt(), member.getId(), seller.getSellList(), material.getId());
            sellListItemRepository.save(sellListItem);
        }

        return CreateBuyInfoResponse.of(buyInfo.getId());
    }

    /**
     * 구매에 사용되는 랜덤 코드 생성 관리
     * @return
     */
    public String getRandomCode(){
        // 랜덤 코드 생성
        String generatedCode = generateRandomCode();

        // 최근 1주일 동안 생성된 코드 리스트 가져옴
        List<String> existingCodes = getRecentlyGeneratedCodes();

        // 중복 여부 확인
        while (isCodeDuplicate(generatedCode, existingCodes)) {
            // 중복된 경우 다시 코드를 생성
            generatedCode = generateRandomCode();
        }

        return generatedCode;
    }

    /**
     * 코드 생성
     * @return
     */
    private String generateRandomCode() {
        StringBuilder code = new StringBuilder(12);

        for (int i = 0; i < 12; i++) {
            int randomIndex = random.nextInt(62); // 영문 대소문자 + 숫자
            char randomChar = (randomIndex < 26) ? (char) ('a' + randomIndex) :
                    (randomIndex < 52) ? (char) ('A' + (randomIndex - 26)) :
                            (char) ('0' + (randomIndex - 52));
            code.append(randomChar);
        }

        return code.toString();
    }

    /**
     * 코드 중복 여부 확인
     * @param generatedCode
     * @param existingCodes
     * @return
     */
    private boolean isCodeDuplicate(String generatedCode, List<String> existingCodes) {
        return existingCodes.contains(generatedCode);
    }

    /**
     * 일주일동안 생성된 모든 코드 반환
     * @return
     */
    private List<String> getRecentlyGeneratedCodes() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minus(7, ChronoUnit.DAYS);

        // BuyInfo 테이블에서 생성일이 오늘부터 일주일 전까지인 코드를 가져옴
        List<String> recentCodes = buyInfoRepository
                .findByCreatedAtBetween(oneWeekAgo, LocalDateTime.now())
                .stream()
                .map(BuyInfo::getCode)
                .collect(Collectors.toList());

        return recentCodes;
    }


    /**
     * 구매했던 정보를 가져와 송금안내 페이지에 전달하기 위한 기능
     * @param buyInfoId
     * @return
     */
    public BuyInfoResponse getBuyInfo(Long buyInfoId) {
        List<MaterialNameResponse> materialNameResponseList = new ArrayList<>();
        BuyInfo buyInfo = buyInfoRepository.findById(buyInfoId).orElse(null);
        if(buyInfo==null)
            throw new NotFoundException(NOT_FOUND_BUYINFO_FROM_BUYINFO_ID);
        List<BuyListItem> buyListItems = buyInfo.getBuyListItems();
        for(BuyListItem buyListItem : buyListItems){
            MaterialNameResponse materialNameResponse = MaterialNameResponse.of(buyListItem.getMaterial().getName());
            materialNameResponseList.add(materialNameResponse);
        }
        return BuyInfoResponse.of(materialNameResponseList, buyInfo);
    }

    /**
     * 구매 취소에 해당하는 기능을 수행
     * 이미 송금을 했다면 취소 못하게 예외처리
     * 구매인포의 상태를 취소로, 판매자의 판매리스트 상태를 취소로
     * @param buyInfoId
     * @return
     */
    public BuyCancelResponse doBuyCancel(Long buyInfoId) {
        BuyInfo buyInfo = buyInfoRepository.findById(buyInfoId).orElse(null);
        if(buyInfo == null)
            throw new NotFoundException(NOT_FOUND_BUYINFO_FROM_BUYINFO_ID);

        if(buyInfo.getStatus().equals("afterPay"))
            throw new CancelAfterPayException(INVALID_CANCEL_REQUEST);


        //status 를 cancel로
        buyInfo.setStatus("cancel");
        buyInfoRepository.save(buyInfo);

        //구매자의 Sellitemlist의 Status를 다 Cancel로 바꿔야 함
        List<Material> materials = buyInfo.getBuyListItems().stream().map(BuyListItem::getMaterial).toList();
        for(Material m : materials){
            //buyer id랑 matera
            SellListItem sellListItem = sellListItemRepository.findByBuyerAndMaterialId(buyInfo.getBuyerId(), m.getId());
            sellListItem.setStatus("cancel");
            sellListItemRepository.save(sellListItem);
        }
        return BuyCancelResponse.of("구매가 성공적으로 취소됐습니다");
    }

    public String checkRefundInfo(Long buyInfoId) {
        BuyInfo buyInfo = buyInfoRepository.findById(buyInfoId).orElse(null);
        if(buyInfo == null)
            throw new NotFoundException(NOT_FOUND_BUYINFO_FROM_BUYINFO_ID);

        if(buyInfo.getStatus().equals("buyComplete"))
            throw new PaymentsException(INVALID_REFUND_REQUEST);

        List<BuyListItem> buyListItems = buyInfo.getBuyListItems();
        for(BuyListItem buyListItem : buyListItems){
            if(buyListItem.getIsDown())
                throw new PaymentsException(INVALID_REFUND_REQUEST);
        }

        return "환불 요청이 가능합니다";
    }

    public String createRefundInfo(Long buyInfoId, RefundRequest refundRequest) {

        RefundInfo refundInfo = refundRepository.findByBuyInfoId(buyInfoId);

        if(refundInfo != null){
            throw new PaymentsException(OVERLAP_REFUND_REQUEST);
        }

        BuyInfo buyInfo = buyInfoRepository.findById(buyInfoId).orElse(null);
        if(buyInfo == null)
            throw new NotFoundException(NOT_FOUND_BUYINFO_FROM_BUYINFO_ID);

        RefundInfo newRefundInfo = RefundInfo.of(buyInfo, refundRequest.getAccount());
        refundRepository.save(newRefundInfo);
        return "정상적으로 환불요청이 접수되었습니다.";
    }
}
