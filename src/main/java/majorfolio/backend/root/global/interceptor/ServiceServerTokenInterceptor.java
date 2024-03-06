/**
 * classname (IdTokenInterceptor)
 *
 * Version Information(0.0.1)
 *
 * Date(2024.01.23)
 *
 * Copyright Notice (Majorfolio)
 */
package majorfolio.backend.root.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.admin.entity.Admin;
import majorfolio.backend.root.domain.admin.repository.AdminRepository;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.repository.MemberRepository;
import majorfolio.backend.root.global.exception.AdminException;
import majorfolio.backend.root.global.exception.JwtExpiredException;
import majorfolio.backend.root.global.exception.JwtInvalidException;
import majorfolio.backend.root.global.exception.UserException;
import majorfolio.backend.root.global.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.*;
import static majorfolio.backend.root.global.status.EndPointStatusEnum.*;

/**
 * 서비스에서 발행한 JWT가 유효한지 사전에 체크하는 interceptor 구현
 *
 * @author 김영록
 * @version 0.0.1
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ServiceServerTokenInterceptor implements HandlerInterceptor {
    @Value("${jwt.secret}")
    private String secretKey;


    private final AdminRepository adminRepository;
    private final MemberRepository memberRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userToken = request.getAttribute("token").toString();
        String tokenType = JwtUtil.getTokenHeader(userToken).get("typ").toString();
        log.info(tokenType);

        if(!tokenType.equals("access_token")){
            //토큰 타입이 액세스 토큰이 아닐 경우
            throw new JwtInvalidException(INVALID_TOKEN);
        }

        if(JwtUtil.isExpired(userToken, secretKey)){
            // 토큰이 만료되었다면 예외 던지기
            throw new JwtExpiredException(EXPIRED_TOKEN);
        }

        //카카오Id 토큰으로부터 받아오기
        Long kakaoId = JwtUtil.getKaKaoId(userToken, secretKey);
        request.setAttribute("kakaoId",kakaoId);
        Long emailId = JwtUtil.getEmailId(userToken, secretKey);
        request.setAttribute("emailId", emailId);
        Long memberId = JwtUtil.getMemberId(userToken, secretKey);
        request.setAttribute("memberId", memberId);

        String requestUrl = request.getRequestURI();
        String[] requestUrlDomain = requestUrl.split("/");
        log.info("url : " + requestUrl);
        //만약 요청 url이 /admin/**이라면
        if(requestUrlDomain[1].equals(ADMIN.getDomain())){
            isAdmin(memberId);
        }

        //대학 인증이 필요한 url
        List<String> needUnivAuth = List.of(
                ASSIGNMENT.getDomain(), MY.getDomain(), LIBRARY.getDomain(),
                PAYMENTS.getDomain(), TRANSACTION.getDomain());

        //만약 대학인증이 필요한 url에 접속하려고 하려면
        if(emailId == 0 && needUnivAuth.contains(requestUrl)){
            //과제 상세페이지 조회 제외하곤
            if(!requestUrlDomain[requestUrlDomain.length-1].equals(DETAIL.getDomain())){
                throw new UserException(NOT_UNIV_AUTH);
            }
        }

        return true;
    }

    /**
     * 운영자인지 아닌지 확인
     * @param memberId
     */
    public void isAdmin(Long memberId){
        Member member = memberRepository.findById(memberId).get();

        try {
            Admin admin = adminRepository.findByMember(member);
            log.info(admin.toString());
        }catch (NullPointerException e){
            // 예외처리
            throw new AdminException(NOT_ADMIN_USER);
        }
    }
}


