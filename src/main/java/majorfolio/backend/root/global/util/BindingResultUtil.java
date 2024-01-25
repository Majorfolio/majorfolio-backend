/**
 * BindingResultUtil
 *
 * 0.0.1
 *
 * 2024.01.25
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.util;

import org.springframework.validation.BindingResult;

/**
 * 요청 객체에서 validate관련 어노테이션이 사용되고
 * 거기서 오류가 나면 message= "..." 형태로 적힌것을 볼 수 있는데
 * 그 message를 가지고 오는 클래스라고 보면 된다.
 *
 * @author 김영록
 * @version 0.0.1
 */
public class BindingResultUtil {
    public static String getErrorMessages(BindingResult bindingResult) {
        StringBuilder errorMessages = new StringBuilder();
        bindingResult.getAllErrors()
                .forEach(error -> errorMessages.append(error.getDefaultMessage()).append(". "));
        return errorMessages.toString();
    }
}
