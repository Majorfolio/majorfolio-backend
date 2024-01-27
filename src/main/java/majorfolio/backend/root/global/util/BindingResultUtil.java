/**
 * BindingResultUtil
 *
 * 0.0.1
 *
 * 2024.01.28
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.util;

import org.springframework.validation.BindingResult;

/**
 * @validate어노테이션 사용시에 오류가 나면 그 오류메시지를 반환해주기 위한 클래스이다
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
