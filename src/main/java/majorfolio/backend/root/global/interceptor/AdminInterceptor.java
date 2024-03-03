package majorfolio.backend.root.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.admin.entity.Admin;
import majorfolio.backend.root.domain.admin.repository.AdminRepository;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.NoSuchElementException;

/**
 * admin인지 아닌지 판별해주는 interceptor클래스
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AdminInterceptor implements HandlerInterceptor {
    private final AdminRepository adminRepository;
    private final MemberRepository memberRepository;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //member ID 가져오기
        Long memberId = Long.parseLong(request.getAttribute("memberId").toString());
        Member member = memberRepository.findById(memberId).get();

        try {
            Admin admin = adminRepository.findByMember(member);
        }catch (NoSuchElementException e){
            // 예외처리
        }

        return true;
    }
}
