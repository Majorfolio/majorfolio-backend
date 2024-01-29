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
import majorfolio.backend.root.domain.member.dto.*;
import majorfolio.backend.root.domain.member.entity.*;
import majorfolio.backend.root.domain.member.repository.*;
import majorfolio.backend.root.domain.university.repository.UniversityRepository;
import majorfolio.backend.root.global.exception.*;
import majorfolio.backend.root.global.util.JwtUtil;
import majorfolio.backend.root.global.util.RandomCodeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final MemberRepository memberRepository;
    private final BasketRepository basketRepository;
    private final BuyListRepository buyListRepository;
    private final SellListRepository sellListRepository;
    private final FollwerListRepository follweListRepository;
    private final CouponBoxRepository couponBoxRepository;


    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${spring.mail.username}")
    private String majorfolioMail;

    public MemberService(JavaMailSender javaMailSender,
                         KakaoSocialLoginRepository kakaoSocialLoginRepository,
                         UniversityRepository universityRepository,
                         EmailDBRepository emailDBRepository, MemberRepository memberRepository, BasketRepository basketRepository, BuyListRepository buyListRepository, SellListRepository sellListRepository, FollwerListRepository follweListRepository, CouponBoxRepository couponBoxRepository) {
        this.javaMailSender = javaMailSender;
        this.kakaoSocialLoginRepository = kakaoSocialLoginRepository;
        this.universityRepository = universityRepository;
        this.emailDBRepository = emailDBRepository;
        this.memberRepository = memberRepository;
        this.basketRepository = basketRepository;
        this.buyListRepository = buyListRepository;
        this.sellListRepository = sellListRepository;
        this.follweListRepository = follweListRepository;
        this.couponBoxRepository = couponBoxRepository;
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
        Long emailId;
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
            emailId = 0L;
        }
        else{
            log.info("memberId = {}", memberId);
            emailId = emailDBRepository.findByMember(memberRepository.findById(memberId).get()).getId();
        }
        if(memberId != 0){
            isMember = true;
        }

        String accessToken = JwtUtil.createAccessToken(memberId, kakaoSocialLogin.getId(), emailId, secretKey);

        String refreshToken = JwtUtil.createRefreshToken(secretKey);

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
        if(!checkSchoolEmail(email)){
            // 학교 이메일이 아닐 경우
            throw new NotSchoolEmailException(NOT_SCHOOL_EMAIL);
        }
        if(!checkOverlapEmail(email)){
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

        try {
            javaMailSender.send(simpleMailMessage);
        }
        catch (Exception e){
            throw new SendEmailException(SEND_ERROR);
        }

        LocalDateTime expire = LocalDateTime.now().plusDays(1); // 코드 유효기간 1일

        //DB에 저장
        emailDB.setExpire(expire);
        emailDB.setEmail(email);
        emailDB.setCode(code);
        emailDB.setStatus(false);
        emailDBRepository.save(emailDB);

        return EmailResponse.of(emailDB.getId(), code);
    }

    public SignupResponse signup(SignupRequest signupRequest, Long kakaoId){
        EmailDB emailDB;
        if(!signupRequest.getServiceAgree()
        || !signupRequest.getPersonalAgree()){
            // 개인정보나 서비스 이용약관에 비동의 시
            throw new NotSatisfiedAgreePolicyException(NOT_SATISFIED_AGREE_POLICY);
        }
        try {
            // 이메일 레포지토리 생성
            emailDB = emailDBRepository.findById(signupRequest.getEmailId()).get();
        }catch (NoSuchElementException e){
            throw new UserException(INVALID_USER_VALUE);
        }

        // 장바구니, 구매리스트, 판매리스트, 쿠폰박스, 팔로워 목록 만들어두기
        Basket basket = Basket.builder().build();
        basketRepository.save(basket);
        BuyList buyList = BuyList.builder().build();
        buyListRepository.save(buyList);
        SellList sellList = SellList.builder().build();
        sellListRepository.save(sellList);
        CouponBox couponBox = CouponBox.builder().build();
        couponBoxRepository.save(couponBox);
        FollwerList follwerList = FollwerList.builder().build();
        follweListRepository.save(follwerList);

        Member member = Member.of(signupRequest.getNickName(), signupRequest.getUniversityName(),
                signupRequest.getMajor1(), signupRequest.getMajor2(), signupRequest.getStudentId(),
                signupRequest.getPersonalAgree(), signupRequest.getServiceAgree(), signupRequest.getMarketingAgree(),
                basket, buyList, sellList, follwerList, couponBox);

        memberRepository.save(member);

        log.info(String.valueOf(kakaoId));
        // 카카오 레포에도 memberId값 저장
        KakaoSocialLogin kakaoSocialLogin = kakaoSocialLoginRepository.findById(kakaoId).get();
        kakaoSocialLogin.setMember(member);
        kakaoSocialLoginRepository.save(kakaoSocialLogin);

        // 이메일 레포에도 memberId값 저장
        emailDB.setMember(member);
        emailDBRepository.save(emailDB);

        //액세스 토큰 생성
        String accessToken = JwtUtil.createAccessToken(member.getId(), kakaoSocialLogin.getId(), emailDB.getId(), secretKey);

        return SignupResponse.of(member.getId(), accessToken);
    }

    /**
     * 이메일 코드 대조 API 서비스 구현
     * @param emailCodeRequest
     */
    public String emailCodeCompare(EmailCodeRequest emailCodeRequest){
        Long emailId = emailCodeRequest.getEmailId();
        String code = emailCodeRequest.getCode();
        if(!checkExpireCode(emailId)){
            //인증코드 만료시
            throw new ExpiredCodeException(EXPIRED_CODE);
        }
        EmailDB emailDB = emailDBRepository.findById(emailId).get();
        String answer = emailDB.getCode();

        if(!code.equals(answer)){
            // 인증 코드가 다를때
            throw new NotEqualCodeException(NOT_EQUAL_CODE);
        }

        emailDB.setStatus(true);
        emailDB.setEmailDate(LocalDateTime.now());

        emailDBRepository.save(emailDB);

        return "";
    }


    /**
     * 학교이메일인지 아닌지 검사해주는 로직
     * @param email
     * @return
     */
    public Boolean checkSchoolEmail(String email){
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
    public Boolean checkOverlapEmail(String email){
        if(emailDBRepository.existsEmailDBByEmailAndStatus(email, true)){
            // 이미 인증한 이메일 일때
            return false;
        }
        return true;
    }

    /**
     * 인증 코드가 만료되었는지 여부 검사메소드(인증코드 받고 하루뒤에 그걸 입력하면 인증 안되게 하기 위함)
     * @param id
     * @return
     */
    public Boolean checkExpireCode(Long id){
        LocalDateTime exiredate = emailDBRepository.findById(id).get().getExpire();
        if(exiredate.isBefore(LocalDateTime.now())){
            return false;
        }
        return true;
    }
}
