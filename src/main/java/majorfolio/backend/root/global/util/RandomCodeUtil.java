/**
 * RandomCodeUtil
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.util;

import java.util.Random;

/**
 * RandonCode를 생성하는 메소드를 담은 클래스이다.
 *
 * @author 김영록
 * @version 0.0.1
 */
public class RandomCodeUtil {

    /**
     * 랜덤코드를 생성해주는 메소드이다.
     * @param randomCodeLength 랜덤 코드의 길이정보를 담은 파라미터
     * @return
     */
    public static String GenerateRandomCode(int randomCodeLength){
        int leftLimit = 48; // '0'
        int rightLimit = 122; // 'z'
        int targetStringLength = randomCodeLength;
        Random random = new Random();

        String generatedString = random.ints(leftLimit,rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
