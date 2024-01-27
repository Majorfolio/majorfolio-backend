/**
 * EmailResponse
 *
 * 0.0.1
 *
 * 2024.01.28
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이메일 인증 요청 API시 응답 객체 정의
 *
 * @author 김영록
 * @version 0.0.1
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmailResponse {
    private String email;
    private String code;

    public static EmailResponse of(String email, String code){
        return EmailResponse
                .builder()
                .email(email)
                .code(code)
                .build();
    }
}
