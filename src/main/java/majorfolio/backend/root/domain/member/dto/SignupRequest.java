/**
 * SignupRequest
 *
 * 0.0.1
 *
 * 2024.01.25
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

/**
 * 회원가입시 요청 형식을 정의해놓은 클래스
 * 이때 email에 @Email 어노테이션 안붙인 이유
 * -> 게스트 로그인 때문에, email로 null값이 들어올 수 있어서
 *
 * @author 김영록
 * @version 0.0.1
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignupRequest {
    private String email;
    private Boolean emailVerified;
    @Length(min = 1, max = 8,
    message = "nickName : 최소 1자리 최대 8자리 까지 가능합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$",
    message = "닉네임은 영어, 숫자, 한글로만 이루어져있어야 합니다.")
    private String nickName;
    @NotBlank(message = "학교 이름이 비어있습니다.")
    private String universityName;
    @NotBlank(message = "studentId : 학번이 비어있습니다.")
    private String studentId;
    @NotBlank(message = "major1 : 주전공란이 비어있습니다.")
    private String major1;
    @Nullable
    private String major2;
    private Boolean personalInformationIsagree;
    private Boolean serviceIsagree;
}
