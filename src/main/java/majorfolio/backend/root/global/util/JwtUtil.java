package majorfolio.backend.root.global.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;

import java.util.*;

@Slf4j
public class JwtUtil {
    public static Map<String, String> getTokenInfo(String token){

//        Map<String, Object> requestArray = jsonParser.parseMap(token);

        StringTokenizer st = new StringTokenizer(token, ".");

        Map<String, String> requestInfo = new HashMap<>();

        //requestInfo.put("request", requestArray.get("idToken").toString());
        requestInfo.put("header",st.nextToken());
        requestInfo.put("payload", st.nextToken());

        return requestInfo;
    }

    public static Map<String, Object> getTokenPayload(String token){
        JsonParser jsonParser = new BasicJsonParser();
        Map<String, String> requestInfo = getTokenInfo(token);

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String decodedPayload = new String(decoder.decode(requestInfo.get("payload")));

        Map<String, Object> payloadArray = jsonParser.parseMap(decodedPayload);
        return payloadArray;
    }

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

    public static String getUserName(String token, String secretKey){
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("userName", String.class);
    }

    public static boolean isExpired(String token, String secretKey){
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getExpiration().before(new Date()); // expired 된게 지금보다 전인가? -> 그러면 만료된거임
    }
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
