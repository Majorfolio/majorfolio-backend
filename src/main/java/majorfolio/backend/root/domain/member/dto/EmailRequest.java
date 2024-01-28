/**
 * EmailRequest
 *
 * 0.0.1
 *
 * 2024.01.28
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이메일 인증 요청 API 에서 요청 객체
 *
 * @author 김영록
 * @version 0.0.1
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EmailRequest {
    @Email(message = "email: 올바르지 않은 이메일 형식입니다.")
    @NotBlank(message = "email: 이메일이 비어있습니다.")
    private String email;
}
