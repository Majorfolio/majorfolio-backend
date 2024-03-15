package majorfolio.backend.root.global.status;

import lombok.RequiredArgsConstructor;

/**
 *  하드코딩 방지를 위해 DB에 status값을 상수 클래스로 빼놓기
 * @author 김영록
 */
@RequiredArgsConstructor
public enum StatusEnum implements DBstatus{
    //Member테이블 쪽 status
    ACTIVE("Member", "active", "활성상태"),
    DORMANCY("Member", "dormancy", "휴면상태"),
    BEN("Member", "ben", "밴 상태"),
    DELETE("Member", "delete", "탈퇴 상태"),
    CREATING("Member", "creating", "만들고 있는 상태(상세정보 입력 안됨"),

    //Material테이블 쪽 status
    MATERIAL_ACTIVE("Material", "active", "활성상태"),
    MATERIAL_REVIEWING("Material", "reviewing", "대기상태"),
    MATERIAL_OLD("Material", "old", "수정전 파일"),
    MATERIAL_BEN("Material", "ben", "신고당한 파일"),

    //BuyInfo테이블 쪽 status
    BUYINFO_BUYCOMPLETE("BuyInfo", "buyComplete", "구매확정"),
    BUYINFO_AFTER_PAY("BuyInfo", "afterPay", "구매완료"),
    BUYINFO_CANCLE("BuyInfo", "cancle", "구매취소"),
    BUYINFO_AFTER_REFUND("BuyInfo", "afterRefund", "환불완료");


    private final String DBTable;
    private final String status;
    private final String description;
    @Override
    public String getDBTable() {
        return DBTable;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public String getDescription() {
        return description;
    }


}
