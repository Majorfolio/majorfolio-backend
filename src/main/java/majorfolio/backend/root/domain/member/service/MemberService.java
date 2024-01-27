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
import majorfolio.backend.root.domain.member.dto.EmailRequest;
import majorfolio.backend.root.domain.member.dto.EmailResponse;
import majorfolio.backend.root.domain.member.dto.LoginResponse;
import majorfolio.backend.root.domain.member.entity.EmailDB;
import majorfolio.backend.root.domain.member.entity.KakaoSocialLogin;
import majorfolio.backend.root.domain.member.repository.EmailDBRepository;
import majorfolio.backend.root.domain.member.repository.KakaoSocialLoginRepository;
import majorfolio.backend.root.domain.university.repository.UniversityRepository;
import majorfolio.backend.root.global.exception.NotSchoolEmailException;
import majorfolio.backend.root.global.exception.OverlapEmailException;
import majorfolio.backend.root.global.exception.SendEmailException;
import majorfolio.backend.root.global.util.JwtUtil;
import majorfolio.backend.root.global.util.RandomCodeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.NoSuchElementException;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.*;

/**
 * MemeberController에서 수행되는 서비스 동작을 정의한 클래스
 * @author 김영록
 * @version 0.0.1
 */
@Service
@Slf4j
public class MemberService {
    private final JavaMailSender javaMailSender;
    private final KakaoSocialLoginRepository kakaoSocialLoginRepository;
    private final UniversityRepository universityRepository;
    private final EmailDBRepository emailDBRepository;


    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${spring.mail.username}")
    private String majorfolioMail;

    public MemberService(JavaMailSender javaMailSender,
                         KakaoSocialLoginRepository kakaoSocialLoginRepository,
                         UniversityRepository universityRepository,
                         EmailDBRepository emailDBRepository) {
        this.javaMailSender = javaMailSender;
        this.kakaoSocialLoginRepository = kakaoSocialLoginRepository;
        this.universityRepository = universityRepository;
        this.emailDBRepository = emailDBRepository;
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
        Long memberId;
        KakaoSocialLogin kakaoSocialLogin = kakaoSocialLoginRepository.findByKakaoNumber(kakaoId);
        if(kakaoSocialLogin == null){
            // 카카오 소셜 로그인 객체가 없으면 만들어서 DB에 저장한다.
            kakaoSocialLogin = KakaoSocialLogin.builder().build();
            kakaoSocialLoginRepository.save(kakaoSocialLogin);
        }
        try {
            memberId = kakaoSocialLoginRepository.findByKakaoNumber(kakaoId).getMember().getId();
        }catch (NoSuchElementException | NullPointerException e){
            memberId = 0L;
        }
        if(memberId == 0){
            log.info("memberId is null");
        }
        else{
            log.info("memberId = {}", memberId);
        }
        if(memberId != 0){
            isMember = true;
        }

        Long expireAccessToken = Duration.ofHours(2).toMillis(); // 만료 시간 2시간
        String accessToken = JwtUtil.createAccessToken(memberId, kakaoId, secretKey, expireAccessToken);

        Long expireRefreshToken = Duration.ofDays(14).toMillis(); // 만료 시간 2주
        String refreshToken = JwtUtil.createRefreshToken(secretKey, expireRefreshToken);

        //리프레쉬 토큰 db에 저장
        kakaoSocialLogin.setKakaoNumber(kakaoId);
        kakaoSocialLogin.setState(state);
        kakaoSocialLogin.setNonce(nonce);
        kakaoSocialLogin.setRefreshToken(refreshToken);
        kakaoSocialLoginRepository.save(kakaoSocialLogin);

        return LoginResponse.of(isMember, accessToken, refreshToken);
    }

    /**
     * 이메일 인증 요청 API의 서비스를 정의
     * 우선 이메일 유효성 검사를 한 뒤에 랜덤 코드를 발급해주고
     * JavaMailSender를 통해 메일을 보내줌
     * EmailDB에 저장(아직 인증은 안된 상태여서 state는 false, emailDate는 null값 들어감)
     * 그리고 응답으로 이메일주소와 코드를 보내줌
     * @param emailRequest
     * @return
     */
    public EmailResponse emailAuth(EmailRequest emailRequest){
        String email = emailRequest.getEmail();
        if(!isSchoolEmail(email)){
            // 학교 이메일이 아닐 경우
            throw new NotSchoolEmailException(NOT_SCHOOL_EMAIL);
        }
        if(!isOverlapEmail(email)){
            //이미 인증할 이메일일 경우
            throw new OverlapEmailException(OVERLAP_EMAIL);
        }

        EmailDB emailDB = emailDBRepository.findByEmail(email);
        if(emailDB == null){
            // 해당 이메일 주소를 가진 데이터가 없으면 새로 생성
            emailDB = EmailDB.builder().build();
            emailDBRepository.save(emailDB);
        }

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(emailRequest.getEmail());
        simpleMailMessage.setSubject("Majorfolio 이메일 인증 코드 입니다.");
        simpleMailMessage.setFrom(majorfolioMail);

        String code = RandomCodeUtil.GenerateRandomCode(6);
        simpleMailMessage.setText("인증코드는: " + code + "입니다.");

        javaMailSender.send(simpleMailMessage);
        try {
            javaMailSender.send(simpleMailMessage);
        }
        catch (Exception e){
            throw new SendEmailException(SEND_ERROR);
        }

        LocalDate expire = LocalDate.now().plusDays(1); // 코드 유효기간 1일

        //DB에 저장
        emailDB.setExpire(expire);
        emailDB.setEmail(email);
        emailDB.setCode(code);
        emailDB.setStatus(false);
        emailDBRepository.save(emailDB);

        return EmailResponse.of(email, code);
    }


    /**
     * 학교이메일인지 아닌지 검사해주는 로직
     * @param email
     * @return
     */
    public Boolean isSchoolEmail(String email){
        String domain = email.split("@")[1];
        if(universityRepository.findByDomain(domain) == null){
            //학교 이메일이 아닐때
            return false;
        }
        return true;
    }

    /**
     * 이미 인증된 이메일을 또 다시 입력받는건 아닌지 검증
     * @param email
     * @return
     */
    public Boolean isOverlapEmail(String email){
        if(emailDBRepository.existsEmailDBByEmailAndStatus(email, true)){
            // 이미 인증한 이메일 일때
            return false;
        }
        return true;
    }
}
