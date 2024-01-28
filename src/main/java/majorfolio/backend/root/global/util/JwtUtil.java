/**
 * JwtUtil
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.global.exception.JwtExpiredException;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;

import java.util.*;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.EXPIRED_TOKEN;

/**
 * Jwt를 다루는 여러 메소드들을 취합해 놓은 클래스임
 *
 * @author 김영록
 * @version 0.0.1
 */
@Slf4j
public class JwtUtil {
    /**
     * 토큰을 해더와 페이로드로 분리해주는 메소드이다.
     * @param token token을 파라미터로 넘겨줌
     * @return 헤더와 페이로드를 담고힜는 map형식의 자료구조
     */
    public static Map<String, String> getTokenInfo(String token){

//        Map<String, Object> requestArray = jsonParser.parseMap(token);

        StringTokenizer st = new StringTokenizer(token, ".");

        Map<String, String> requestInfo = new HashMap<>();

        //requestInfo.put("request", requestArray.get("idToken").toString());
        requestInfo.put("header",st.nextToken());
        requestInfo.put("payload", st.nextToken());

        return requestInfo;
    }

    /**
     * 토큰의 페이로드 부분을 디코딩해주는 메소드이다.
     * @param token token을 파라미터로 넘겨줌
     * @return
     */
    public static Map<String, Object> getTokenPayload(String token){
        JsonParser jsonParser = new BasicJsonParser();
        Map<String, String> requestInfo = getTokenInfo(token);

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String decodedPayload = new String(decoder.decode(requestInfo.get("payload")));

        Map<String, Object> payloadArray = jsonParser.parseMap(decodedPayload);
        return payloadArray;
    }

    /**
     * 토큰의 헤더 부분을 디코딩해주는 메소드이다.
     * @param token token을 파라미터로 넘겨줌
     * @return
     */
    public static Map<String, Object> getTokenHeader(String token){
        JsonParser jsonParser = new BasicJsonParser();
        Map<String, String> requestInfo = getTokenInfo(token);
        log.info(requestInfo.toString());

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String decodedHeader = new String(decoder.decode(requestInfo.get("header")));

        Map<String, Object> headerArray = jsonParser.parseMap(decodedHeader);
        log.info(headerArray.toString());
        return headerArray;
    }

    /**
     * 토큰에서 유저의 이름을 뽑아내는 메소드 이다.
     * @param token token을 파라미터로 넘겨줌
     * @param secretKey 토큰을 디코딩하기 위해 쓰이는 시크릿키
     * @return
     */
    public static Long getKaKaoId(String token, String secretKey){
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("kakaoId", Long.class);
    }

    /**
     @@ -100,7 +100,13 @@ public static String getUserName(String token, String secretKey){
      * @return
     */
    public static boolean isExpired(String token, String secretKey){
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey)
                    .build().parseClaimsJws(token).getBody()
                    .getExpiration().before(new Date()); // expired 된게 지금보다 전인가? -> 그러면 만료된거임
        }catch (ExpiredJwtException e){
            throw new JwtExpiredException(EXPIRED_TOKEN);
        }
    }

    /**
     * 액세스 토큰을 발급해주는 메소드이다.
     * 다음 2개 파라미터는 액세스 토큰 claim에 넣어줄 정보를 나타냄
     * @param userId member테이블의 id정보
     * @param kakaoId user_token테이블의 kakaoId정보
     *                -----
     * @param secretKey 토큰을 디코딩하기 위해 쓰이는 시크릿키
     * @param expiredMs 만료시간 설정(2시간으로 설정함)
     * @return
     */
    public static String createAccessToken(Long userId, Long kakaoId, String secretKey, Long expiredMs){
        //token에 들어있는 유저 정보를 사용하기 위함
        // token에 유저 정보 담기 위해 claim사용
        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        claims.put("kakaoId", kakaoId);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, "access_token")
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact(); //jwt토큰 발행
    }

    /**
     * 리프레쉬 토큰을 발급해주는 메소드이다.
     * @param secretKey 토큰을 디코딩하기 위해 쓰이는 시크릿키
     * @param expiredMs 만료시간 설정(2주로 설정함)
     * @return
     */
    public static String createRefreshToken(String secretKey, Long expiredMs){
        Claims claims = Jwts.claims();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, "refresh_token")
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact(); //jwt토큰 발행
    }
}
