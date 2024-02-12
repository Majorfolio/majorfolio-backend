package majorfolio.backend.root.domain.payments.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 구매할 자료의 아이디 리퀘스트
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MaterialIdRequest {
    private Long assignmentId;
}
