package majorfolio.backend.root.domain.report.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.member.entity.KakaoSocialLogin;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.service.MemberGlobalService;
import majorfolio.backend.root.domain.report.dto.request.ReportMaterialRequest;
import majorfolio.backend.root.domain.report.dto.request.ReportMemberRequest;
import majorfolio.backend.root.domain.report.entity.MaterialReport;
import majorfolio.backend.root.domain.report.entity.MemberReport;
import majorfolio.backend.root.domain.report.repository.MaterialReportRepository;
import majorfolio.backend.root.domain.report.repository.MemberReportRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final MemberGlobalService memberGlobalService;
    private final MemberReportRepository memberReportRepository;
    private final MaterialReportRepository materialReportRepository;
    public String reportMember(ReportMemberRequest reportMemberRequest, HttpServletRequest request) {
        KakaoSocialLogin kakaoSocialLogin = memberGlobalService.getMemberByToken(request);
        Member member = kakaoSocialLogin.getMember();
        MemberReport memberReport = MemberReport.of(member.getId(), reportMemberRequest.getReportedMemberId(), reportMemberRequest.getReason(), reportMemberRequest.getDescription());
        memberReportRepository.save(memberReport);
        return "정상적으로 유저 신고가 접수되었습니다.";
    }

    public String reportMaterial(ReportMaterialRequest reportMaterialRequest, HttpServletRequest request) {
        KakaoSocialLogin kakaoSocialLogin = memberGlobalService.getMemberByToken(request);
        Member member = kakaoSocialLogin.getMember();
        MaterialReport materialReport = MaterialReport.of(member.getId(), reportMaterialRequest.getReportedMaterialId(), reportMaterialRequest.getReason(), reportMaterialRequest.getDescription());
        materialReportRepository.save(materialReport);
        return "정상적으로 과제물 신고가 접수되었습니다.";
    }
}
