package majorfolio.backend.root.global.util;

import java.util.Random;

public class RandomCodeUtil {
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
