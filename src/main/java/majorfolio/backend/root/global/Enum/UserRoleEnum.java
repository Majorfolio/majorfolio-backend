/**
 * UserRoleEnum
 *
 * 0.0.1
 *
 * 2024.01.25
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 메이저폴리오 사용자들의 권한을 정희해놓은 상수 클래스
 *
 * @author 김영록
 * @version 0.0.1
 */
@RequiredArgsConstructor
@Getter
public enum UserRoleEnum {
    GUEST("guest"),
    MEMBER("member"),
    ADMIN("admin");
    private final String userRole;
}
