/**
 * SignupRequest
 *
 * 0.0.1
 *
 * 2024.01.30
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * 회원가입 api의 요청형식 정의
 *
 * @author 김영록
 * @version 0.0.1
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignupRequest {
    @NotNull(message = "emailId : 이메일Id를 입력해주세요")
    private Long emailId;
    @NotBlank(message = "nickName : 닉네임을 입력해주세요")
    @Length(min=1, max = 8, message = "nickName : 최소 1자리 최대 8자리 입니다.")
    private String nickName;
    @NotBlank(message = "universityName : 대학명을 입력해주세요")
    private String universityName;
    @NotNull(message = "studentId : 학번을 입력해주세요")
    private int studentId;
    @NotBlank(message = "major1 : 전공을 입력해주세요")
    private String major1;
    private String major2;
    private Boolean personalAgree;
    private Boolean serviceAgree;
    private Boolean marketingAgree;
}
