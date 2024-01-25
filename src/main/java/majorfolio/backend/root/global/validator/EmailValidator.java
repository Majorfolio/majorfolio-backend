/**
 * EmailValidator
 *
 * 0.0.1
 *
 * 2024.01.25
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 이메일이 유효한 형식인지 검사하는 클래스
 * 출처 : https://velog.io/@blaze241/Java-%EC%9D%B4%EB%A9%94%EC%9D%BC-%EC%B2%B4%ED%81%AC-%EC%A0%95%EA%B7%9C%EC%8B%9D-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0
 *
 * @author 김영록
 * @version 0.0.1
 */
public class EmailValidator {
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
