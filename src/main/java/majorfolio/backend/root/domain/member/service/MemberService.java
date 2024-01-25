/**
 * MemeberService
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.service;

import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.member.dto.LoginResponse;
import majorfolio.backend.root.domain.member.dto.RemakeTokenResponse;
import majorfolio.backend.root.domain.member.dto.SignupRequest;
import majorfolio.backend.root.domain.member.dto.SignupResponse;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.entity.UserToken;
import majorfolio.backend.root.domain.member.repository.MemberRepository;
import majorfolio.backend.root.domain.member.repository.UserTokenRepository;
import majorfolio.backend.root.domain.university.entity.MemberUniv;
import majorfolio.backend.root.domain.university.entity.University;
import majorfolio.backend.root.domain.university.repository.MemberUnivRepository;
import majorfolio.backend.root.domain.university.repository.UniversityRepository;
import majorfolio.backend.root.global.Enum.UserRoleEnum;
import majorfolio.backend.root.global.exception.JwtExpiredException;
import majorfolio.backend.root.global.exception.MemberException;
import majorfolio.backend.root.global.util.JwtUtil;
import majorfolio.backend.root.global.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.NoSuchElementException;

import static majorfolio.backend.root.global.Enum.UserRoleEnum.GUEST;
import static majorfolio.backend.root.global.Enum.UserRoleEnum.MEMBER;
import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.*;

/**
 * MemeberController에서 수행되는 서비스 동작을 정의한 클래스
 * @author 김영록
 * @version 0.0.1
 */
@Service
@Slf4j
public class MemberService {
    private final UserTokenRepository userTokenRepository;
    private final MemberRepository memberRepository;

    private final UniversityRepository universityRepository;

    private final MemberUnivRepository memberUnivRepository;


    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String client_id;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirect_uri;

    private final int randomCodeLength = 20;

    public MemberService(UserTokenRepository userTokenRepository, MemberRepository memberRepository, UniversityRepository universityRepository, MemberUnivRepository memberUnivRepository) {
        this.userTokenRepository = userTokenRepository;
        this.memberRepository = memberRepository;
        this.universityRepository = universityRepository;
        this.memberUnivRepository = memberUnivRepository;
    }

    /**
     * /member/login으로 요청시에 수행될 서비스 로직을 정의한 메소드
     * 현재 회원인지 아닌지값, 액세스 토큰, 리프레쉬 토큰을 반환해준다.
     * @param kakaoId
     * @param nonce
     * @param state
     * @return
     */
    public LoginResponse memberLogin(Long kakaoId, String nonce, String state){
        Boolean isMember = false;
        Long userId;
        try {
            userId = memberRepository.findByUserToken(userTokenRepository.findById(kakaoId).get()).getId();
        }catch (NoSuchElementException e){
            userId = 0L;
        }catch (NullPointerException e){
            userId = 0L;
        }
        if(userId == 0){
            log.info("userId is null");
        }
        else{
            log.info("userId = {}", userId);
        }
        if(userId != 0){
            isMember = true;
        }

        Long expireAccessToken = Duration.ofHours(2).toMillis(); // 만료 시간 2시간
        String accessToken = JwtUtil.createAccessToken(userId, kakaoId, secretKey, expireAccessToken);

        Long expireRefreshToken = Duration.ofDays(14).toMillis(); // 만료 시간 2주
        String refreshToken = JwtUtil.createRefreshToken(secretKey, expireRefreshToken);

        //리프레쉬 토큰 db에 저장
        UserToken userToken = UserToken.builder()
                .id(kakaoId)
                .nonce(nonce)
                .state(state)
                .refreshToken(refreshToken)
                .build();
        userTokenRepository.save(userToken);

        return LoginResponse.builder()
                .isMember(isMember)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * 토큰 재발급을 서비스를 구현
     * @param kakaoId
     * @return
     */
    public RemakeTokenResponse remakeTokenResponse(Long kakaoId){
        String oldRefreshToken = userTokenRepository.findById(kakaoId).get().getRefreshToken();
        if(JwtUtil.isExpired(oldRefreshToken, secretKey)){
            throw new JwtExpiredException(EXPIRED_TOKEN);
        }
        Long userId = memberRepository.findByUserToken(userTokenRepository.findById(kakaoId).get()).getId();
        Long expireAccessToken = Duration.ofHours(2).toMillis(); // 만료 시간 2시간

        String accessToken = JwtUtil.createAccessToken(userId, kakaoId,secretKey,expireAccessToken);

        Long expireRefreshToken = Duration.ofDays(14).toMillis(); // 만료 시간 2시간
        String refreshToken = JwtUtil.createRefreshToken(secretKey, expireRefreshToken);

        //리프레쉬 토큰 db에 저장
        UserToken userToken = userTokenRepository.findById(kakaoId).get();
        userToken.setRefreshToken(refreshToken);
        userTokenRepository.save(userToken);

        return RemakeTokenResponse.of(accessToken, refreshToken);
    }

    /**
     * 회원가입 서비스를 구현
     * @param signupRequest
     * @param kakaoId
     * @return
     */
    public SignupResponse signup(SignupRequest signupRequest, Long kakaoId){
        String email = signupRequest.getEmail();
        Boolean emailVerified = signupRequest.getEmailVerified();
        String nickName = signupRequest.getNickName();
        String univerSityName = signupRequest.getUniversityName();
        String studentId = signupRequest.getStudentId();
        String major1 = signupRequest.getMajor1();
        String major2 = signupRequest.getMajor2();
        Boolean personalInformationIsagree = signupRequest.getPersonalInformationIsagree();
        Boolean serviceIsagree = signupRequest.getServiceIsagree();
        String status = MEMBER.getUserRole();

        //이메일이 비어있거나 인증이 안되었을 때
        if(email == null || !emailVerified){
            status = GUEST.getUserRole();
        }
        //이메일 형식을 따르지 않았을때
        if(email != null && !EmailValidator.isValidEmail(email)){
            throw new MemberException(INVALID_USER_VALUE);
        }

        //요청으로 보낸 회원정보를 DB에 담기
        UserToken userToken = userTokenRepository.findById(kakaoId).get();

        Member member = Member.of(email,nickName,emailVerified,personalInformationIsagree,serviceIsagree,
                status, userToken);
        memberRepository.save(member);

        University university = University.of(univerSityName);
        universityRepository.save(university);

        MemberUniv memberUniv = MemberUniv.of(studentId, major1, major2, member, university);
        memberUnivRepository.save(memberUniv);

        Long userId = member.getId();

        Long expireAccessToken = Duration.ofHours(2).toMillis(); // 만료 시간 2시간
        String accessToken = JwtUtil.createAccessToken(userId, kakaoId, secretKey, expireAccessToken);

        return SignupResponse.of(accessToken);
    }
}
