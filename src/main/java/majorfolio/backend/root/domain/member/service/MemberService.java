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

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.member.dto.request.EmailRequest;
import majorfolio.backend.root.domain.member.dto.request.PhoneNumberRequest;
import majorfolio.backend.root.domain.member.dto.response.EmailResponse;
import majorfolio.backend.root.domain.member.dto.response.LoginResponse;
import majorfolio.backend.root.domain.member.dto.response.SignupProgressResponse;
import majorfolio.backend.root.domain.member.entity.EmailDB;
import majorfolio.backend.root.domain.member.entity.KakaoSocialLogin;
import majorfolio.backend.root.domain.member.repository.EmailDBRepository;
import majorfolio.backend.root.domain.member.repository.KakaoSocialLoginRepository;
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
import static majorfolio.backend.root.global.status.StatusEnum.*;

/**
 * MemeberController에서 수행되는 서비스 동작을 정의한 클래스
 * @author 김영록
 * @version 0.0.1
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final JavaMailSender javaMailSender;
    private final KakaoSocialLoginRepository kakaoSocialLoginRepository;
    private final UniversityRepository universityRepository;
    private final EmailDBRepository emailDBRepository;
    private final MemberRepository memberRepository;
    private final BasketRepository basketRepository;
    private final BuyListRepository buyListRepository;
    private final SellListRepository sellListRepository;
    private final FollowerListRepository followerListRepository;
    private final CouponBoxRepository couponBoxRepository;
    private final SellListItemRepository sellListItemRepository;
    private final BuyListItemRepository buyListItemRepository;
    private final TempStorageRepository tempStorageRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${spring.mail.username}")
    private String majorfolioMail;


    /**
     * /member/login으로 요청시에 수행될 서비스 로직을 정의한 메소드
     * 현재 회원인지 아닌지값, 액세스 토큰, 리프레쉬 토큰을 반환해준다.
     * @param kakaoId
     * @param nonce
     * @param state
     * @return
     */
    public LoginResponse memberLogin(Long kakaoId, String nonce, String state){
        boolean isWriteMemberDetailInfo = false;
        Long memberId;
        Long emailId;
        Member member;
        KakaoSocialLogin kakaoSocialLogin = kakaoSocialLoginRepository.findByKakaoNumber(kakaoId);

        if(kakaoSocialLogin == null){
            // 카카오 소셜 로그인 객체가 없으면 만들어서 DB에 저장한다.
            kakaoSocialLogin = KakaoSocialLogin.builder().build();
            kakaoSocialLoginRepository.save(kakaoSocialLogin);
        }
        try {
            emailId = emailDBRepository.findByKakaoSocialLoginAndStatus(kakaoSocialLogin, true).getId();
        }catch (NoSuchElementException | NullPointerException e){
            emailId = 0L;
        }

        try {
            member = kakaoSocialLogin.getMember();
            memberId = member.getId();
            isWriteMemberDetailInfo = checkIsWriteMemberDetailInfo(member);
        }catch (NoSuchElementException | NullPointerException e){
            member = createMember();
            memberId = member.getId();
        }

        //카카오 소셜 로그인 DB에 member값 저장
        kakaoSocialLogin.setMember(member);
        kakaoSocialLoginRepository.save(kakaoSocialLogin);

        String accessToken = JwtUtil.createAccessToken(memberId, kakaoSocialLogin.getId(), emailId, secretKey);

        String refreshToken = JwtUtil.createRefreshToken(kakaoSocialLogin.getId(), secretKey);

        //리프레쉬 토큰 db에 저장
        setKakaoSocialLogin(kakaoSocialLogin, kakaoId, state, nonce, refreshToken);

        return LoginResponse.of(isWriteMemberDetailInfo, memberId, emailId, accessToken, refreshToken);
    }

    //상세 정보 입력했는지 확인
    private boolean checkIsWriteMemberDetailInfo(Member member) {
        return !member.getStatus().equals(CREATING.getStatus());
    }

    private void setKakaoSocialLogin(KakaoSocialLogin kakaoSocialLogin, Long kakaoId, String state,
                                     String nonce, String refreshToken){
        //리프레쉬 토큰 db에 저장
        kakaoSocialLogin.setKakaoNumber(kakaoId);
        kakaoSocialLogin.setState(state);
        kakaoSocialLogin.setNonce(nonce);
        kakaoSocialLogin.setRefreshToken(refreshToken);
        kakaoSocialLoginRepository.save(kakaoSocialLogin);
    }

    // 멤버 생성
    private Member createMember() {
        Member member = Member.builder().build();
        // 장바구니, 구매리스트, 판매리스트, 쿠폰박스, 팔로워 목록 만들어두기
        Basket basket = Basket.builder().build();
        basketRepository.save(basket);
        BuyList buyList = BuyList.builder().build();
        buyListRepository.save(buyList);
        SellList sellList = SellList.builder().build();
        sellListRepository.save(sellList);
        CouponBox couponBox = CouponBox.builder().build();
        couponBoxRepository.save(couponBox);
        FollowerList followerList = majorfolio.backend.root.domain.member.entity.FollowerList.builder().build();
        followerListRepository.save(followerList);
        TempStorage tempStorage = TempStorage.builder().build();
        tempStorageRepository.save(tempStorage);

        member.setBasket(basket);
        member.setBuyList(buyList);
        member.setCouponBox(couponBox);
        member.setFollowerList(followerList);
        member.setTempStorage(tempStorage);
        member.setSellList(sellList);
        member.setStatus(CREATING.getStatus());

        //isMember -> memberStatus값 따라 바뀌도록 해야함

        memberRepository.save(member);

        return member;
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
    public EmailResponse emailAuth(EmailRequest emailRequest, Long memberId){
        String email = emailRequest.getEmail();
        if(!checkSchoolEmail(email)){
            // 학교 이메일이 아닐 경우
            throw new NotSchoolEmailException(NOT_SCHOOL_EMAIL);
        }
        if(!checkOverlapEmail(email)) {
            //이미 인증할 이메일일 경우
            throw new OverlapEmailException(OVERLAP_EMAIL);
        }
        if(memberId > 0){
            Member member = memberRepository.findById(memberId).get();
            if(emailDBRepository.existsByMember(member)){
                throw new UserException(OVERLAP_EMAIL_AUTH);
            }
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
        simpleMailMessage.setText("우리끼리 만드는 과제장터, 메이저폴리오!\n" +
                "학교인증 코드는 아래와 같습니다.\n\n"
                + code
                + "\n\n"+
                "*인증 오류가 있다면, 인증 화면에서 '재발송하기'를 눌러주시거나 다음날 다시 요청해주세요.");

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

        return EmailResponse.of(emailDB.getId());
    }

    /**
     * 회원가입 API서비스 구현
     * @param signupRequest
     * @param kakaoId
     * @return
     */
    public SignupResponse signup(SignupRequest signupRequest, Long kakaoId, Long memberId){
        EmailDB emailDB;
        KakaoSocialLogin kakaoSocialLogin;
        if(!signupRequest.getServiceAgree()
        || !signupRequest.getPersonalAgree()){
            // 개인정보나 서비스 이용약관에 비동의 시
            throw new NotSatisfiedAgreePolicyException(NOT_SATISFIED_AGREE_POLICY);
        }

        try {
            emailDB = emailDBRepository.findById(signupRequest.getEmailId()).get();
            Long emailMemberId = emailDB.getMember().getId();
            if(!emailMemberId.equals(memberId)){
                throw new UserException(INVALID_USER_VALUE);
            }

        }catch (NullPointerException e){
            throw new NoAuthUserException(NOT_UNIV_AUTH);
        }catch (NoSuchElementException e){
            throw new UserException(INVALID_USER_VALUE);
        }


        kakaoSocialLogin = kakaoSocialLoginRepository.findById(kakaoId).get();

        //이미 존재하는 멤버일 때
        if(kakaoSocialLogin.getMember() != null && !kakaoSocialLogin.getMember().getStatus().equals(CREATING.getStatus())){
            throw new UserException(OVERLAP_MEMBER);
        }

        Member member = memberRepository.findById(memberId).get();
        member.setStatus(ACTIVE.getStatus());
        member.setNickName(signupRequest.getNickName());
        member.setMajor1(signupRequest.getMajor1());
        member.setMajor2(signupRequest.getMajor2());
        member.setMarketingAgree(signupRequest.getMarketingAgree());
        member.setNoticeEvent(signupRequest.getPersonalAgree());
        member.setPersonalAgree(signupRequest.getPersonalAgree());
        member.setStudentId(signupRequest.getStudentId());
        member.setUniversityName(signupRequest.getUniversityName());

        memberRepository.save(member);

        log.info(String.valueOf(kakaoId));

        // 이메일 레포에도 memberId값 저장
        emailDB.setMember(member);
        emailDBRepository.save(emailDB);

        //액세스 토큰 생성
        String accessToken = JwtUtil.createAccessToken(member.getId(), kakaoSocialLogin.getId(), signupRequest.getEmailId(), secretKey);

        return SignupResponse.of(member.getId(), accessToken);
    }

    /**
     * 토큰 재발급 서비스 구현
     * @param request
     * @return
     */
    public RemakeTokenResponse remakeToken(HttpServletRequest request){
        // 헤더에서 가져온 리프레쉬 토큰
        String oldRefreshToken = request.getAttribute("token").toString();
        Long kakaoId = Long.parseLong(request.getAttribute("kakaoId").toString());

        // 액세스 토큰 발급받기 위해 memberId, emailId 구하기
        KakaoSocialLogin kakaoSocialLogin = kakaoSocialLoginRepository.findById(kakaoId).get();
        Member member = kakaoSocialLogin.getMember();
        Long memberId = member.getId();
        Long emailId;
        try {
            emailId = emailDBRepository.findByMember(member).getId();
        }catch (NullPointerException e){
            emailId = 0L;
        }

        // 데이터 베이스에 존재하는 리프레쉬 토큰
        String dataRefreshToken = kakaoSocialLoginRepository.findById(kakaoId).get().getRefreshToken();

        if(!oldRefreshToken.equals(dataRefreshToken)){
            throw new JwtInvalidException(INVALID_TOKEN);
        }

        //액세스 및 리프레쉬 토큰 새로 발급
        String accessToken = JwtUtil.createAccessToken(memberId, kakaoId, emailId, secretKey);
        String refreshToken = JwtUtil.createRefreshToken(kakaoId, secretKey);

        //새로 발급 받은 토큰 DB에 다시 저장
        kakaoSocialLogin.setRefreshToken(refreshToken);
        kakaoSocialLoginRepository.save(kakaoSocialLogin);

        return RemakeTokenResponse.of(accessToken, refreshToken);
    }

    /**
     * 닉네임 중복 검사 API구현
     * @param nickname
     * @return
     */
    public String checkNickname(String nickname){
        if(memberRepository.existsMemberByNickName(nickname)){
            throw new OverlapNicknameException(OVERLAP_NICKNAME);
        }

        return "사용가능한 닉네임 입니다.";
    }

    /**
     * 로그아웃 API구현
     * @param request
     * @return
     */
    public String logout(HttpServletRequest request){
        Long kakaoId = Long.parseLong(request.getAttribute("kakaoId").toString());

        KakaoSocialLogin kakaoSocialLogin = kakaoSocialLoginRepository.findById(kakaoId).get();
        kakaoSocialLogin.setRefreshToken("");

        kakaoSocialLoginRepository.save(kakaoSocialLogin);
        return "로그아웃 되었습니다.";
    }

    /**
     * 이메일 코드 대조 API 서비스 구현
     * @param emailCodeRequest
     */
    public String emailCodeCompare(Long emailId, String code, Long kakaoId, Long memberId){
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
        //이메일 인증을 이미 했는데 또 한 경우
        Member member = emailDB.getMember();
        if(member != null){
            throw new UserException(OVERLAP_EMAIL_AUTH);
        }

        emailDB.setStatus(true);
        emailDB.setEmailDate(LocalDateTime.now());
        emailDB.setKakaoSocialLogin(kakaoSocialLoginRepository.findById(kakaoId).get());
        emailDB.setMember(memberRepository.findById(memberId).get());
        emailDBRepository.save(emailDB);

        String accessToken = JwtUtil.createAccessToken(memberId, kakaoId, emailId, secretKey);
        return "";
    }

    public String createPhoneNumber(Long memberId, PhoneNumberRequest phoneNumberRequest){
        Member member = memberRepository.findById(memberId).get();
        member.setPhoneNumber(phoneNumberRequest.getPhoneNumber());
        memberRepository.save(member);
        return "성공";
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


    /**
     * 회원탈퇴 서비스 구현
     * @param memberId
     * @return
     */
    public String deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        KakaoSocialLogin kakaoSocialLogin = kakaoSocialLoginRepository.findByMember(member);
        EmailDB emailDB = emailDBRepository.findByMember(member);


        //거래 내역이 있는지 여부 판단
        //거래 한 적이 있다면 정보들은 남겨두기(status만 탈퇴상태로)
        //거래 한 적이 없다면 정보들 삭제
        if(isMemberDeal(member)){
            makeMemberDelete(member, kakaoSocialLogin);
            emailDB.setEmail(emailDB.getEmail() + " (탈퇴)");
            emailDB.setStatus(false);
            emailDBRepository.save(emailDB);

            return "탈퇴가 성공적으로 진행되었습니다!";
        }

        makeMemberDelete(member, kakaoSocialLogin);

        if(emailDB != null){
            emailDB.setEmail("탈퇴한 회원의 Email");
            emailDB.setStatus(false);
            emailDBRepository.save(emailDB);
        }

        member.setPhoneNumber("");
        memberRepository.save(member);

        return "탈퇴가 성공적으로 진행되었습니다!";
    }

    /**
     * 탈퇴한 회원처리
     * @param member
     */

    public void makeMemberDelete(Member member,
                                 KakaoSocialLogin kakaoSocialLogin) {
        member.setNickName("탈퇴한 회원");
        member.setStatus(DELETE.getStatus());
        memberRepository.save(member);
        kakaoSocialLogin.setRefreshToken("");
        kakaoSocialLogin.setKakaoNumber(0L);
        kakaoSocialLoginRepository.save(kakaoSocialLogin);
    }

    /**
     * 탈퇴할 회원이 거래정보를 가지고 있는지 여부 판단
     * @param member
     * @return
     */
    public boolean isMemberDeal(Member member) {

        BuyList buyList = member.getBuyList();
        SellList sellList = member.getSellList();

        //구매 이력이 있는 경우
        if(buyListItemRepository.existsBuyListItemByBuyList(buyList)){
            return true;
        }

        //판매 이력이 있는 경우
        if(sellListItemRepository.existsSellListItemBySellList(sellList)){
            return true;
        }

        return false;
    }

    public Boolean isMemberHavePhoneNumber(Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        if(member.getPhoneNumber() == null){
            return false;
        }
        return !member.getPhoneNumber().isEmpty();
    }
}
