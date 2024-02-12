package majorfolio.backend.root.domain.payments.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 구매자료 아이디 request
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BuyMaterialListRequest {
    private List<MaterialIdRequest> assignmentIdList;
}
