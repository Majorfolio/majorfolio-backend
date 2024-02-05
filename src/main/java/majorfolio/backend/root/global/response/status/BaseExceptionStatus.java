/**
 * BaseExceptionStatus
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.response.status;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 요청에 대한 응답 형식을 상수형태로 정리하였다.
 *
 * @author 김영록
 * @version 0.01
 */
@RequiredArgsConstructor
public enum BaseExceptionStatus implements ResponseStatus{
    /**
     * 1000: 요청 성공 (OK)
     */
    SUCCESS(1000, HttpStatus.OK.value(), "요청에 성공하였습니다."),

    /**
     * 2000: Request 오류 (BAD_REQUEST)
     */
    BAD_REQUEST(2000, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 요청입니다."),
    URL_NOT_FOUND(2001, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 URL 입니다."),
    METHOD_NOT_ALLOWED(2002, HttpStatus.METHOD_NOT_ALLOWED.value(), "해당 URL에서는 지원하지 않는 HTTP Method 입니다."),

    /**
     * 3000: Server, Database 오류 (INTERNAL_SERVER_ERROR)
     */
    SERVER_ERROR(3000, HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버에서 오류가 발생하였습니다."),
    DATABASE_ERROR(3001, HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터베이스에서 오류가 발생하였습니다."),
    BAD_SQL_GRAMMAR(3002, HttpStatus.INTERNAL_SERVER_ERROR.value(), "SQL에 오류가 있습니다."),

    /**
     * 4000: Jwt 관련 오류
     */
    JWT_ERROR(4000, HttpStatus.UNAUTHORIZED.value(), "JWT에서 오류가 발생하였습니다."),
    TOKEN_NOT_FOUND(4001, HttpStatus.BAD_REQUEST.value(), "토큰이 HTTP Header에 없습니다."),
    UNSUPPORTED_TOKEN_TYPE(4002, HttpStatus.BAD_REQUEST.value(), "지원되지 않는 토큰 형식입니다."),
    INVALID_TOKEN(4003, HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 토큰입니다."),
    MALFORMED_TOKEN(4004, HttpStatus.UNAUTHORIZED.value(), "토큰이 올바르게 구성되지 않았습니다."),
    EXPIRED_TOKEN(4005, HttpStatus.UNAUTHORIZED.value(), "만료된 토큰입니다."),
    TOKEN_MISMATCH(4006, HttpStatus.UNAUTHORIZED.value(), "로그인 정보가 토큰 정보와 일치하지 않습니다."),

    /**
     * 5000: Email관련 오류
     */

    EMAIL_ERROR(5000, HttpStatus.BAD_REQUEST.value(), "올바르지 않은 이메일입니다."),
    NOT_SCHOOL_EMAIL(5001, HttpStatus.BAD_REQUEST.value(), "학교 이메일이 아닙니다."),
    OVERLAP_EMAIL(5002, HttpStatus.BAD_REQUEST.value(), "이미 인증한 이메일 입니다."),
    SEND_ERROR(5003, HttpStatus.INTERNAL_SERVER_ERROR.value(), "메일 서버에 문제가 발생했습니다."),
    EXPIRED_CODE(5004, HttpStatus.BAD_REQUEST.value(), "인증 시간이 지났습니다."),
    NOT_EQUAL_CODE(5005, HttpStatus.BAD_REQUEST.value(), "인증 코드가 다릅니다."),

    /**
     * 6000 : 회원관련 오류
     */

    INVALID_USER_VALUE(6000, HttpStatus.BAD_REQUEST.value(), "회원가입 요청에서 잘못된 값이 존재합니다."),
    NOT_SATISFIED_AGREE_POLICY(6001, HttpStatus.BAD_REQUEST.value(), "필수 동의항목에 동의해야 합니다."),
    OVERLAP_NICKNAME(6002, HttpStatus.BAD_REQUEST.value(), "중복된 닉네임 입니다."),
    NOT_CERTIFICATION_USER(6003, HttpStatus.BAD_REQUEST.value(), "이메일 인증한 사용하가 아닙니다."),

    /**
     * 7000 : 과제관련 오류
     */

    NOT_MATCH_MATERIAL_AND_MEMBER(7001, HttpStatus.BAD_REQUEST.value(), "과제 주인과 현재 사용자가 다릅니다.");



    private final int code;
    private final int status;
    private final String message;
    @Override
    public int getCode() {
        return code;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
