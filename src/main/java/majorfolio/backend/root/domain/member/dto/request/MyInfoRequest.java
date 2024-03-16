package majorfolio.backend.root.domain.member.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MyInfoRequest {
    private String nickName;
    private String major1;
    private String major2;
    private int studentId;
    @Pattern(regexp="\\d{3}-\\d{4}-\\d{4}", message="phoneNumber : 올바른 전화번호 형식이 아닙니다.")
    private String phoneNumber;
}
