package majorfolio.backend.root.domain.admin.service;

import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.material.repository.MaterialRepository;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.repository.MemberRepository;
import majorfolio.backend.root.domain.report.entity.MaterialReport;
import majorfolio.backend.root.domain.report.entity.MemberReport;
import majorfolio.backend.root.domain.report.repository.MaterialReportRepository;
import majorfolio.backend.root.domain.report.repository.MemberReportRepository;
import majorfolio.backend.root.global.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.NOT_PRESENT_MATERIAL;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final MemberReportRepository memberReportRepository;
    private final MaterialReportRepository materialReportRepository;
    private final MemberRepository memberRepository;
    private final MaterialRepository materialRepository;
    public List<MemberReport> getMemberReport() {
        return memberReportRepository.findAll();
    }

    public List<MaterialReport> getMaterialReport() {
        return materialReportRepository.findAll();
    }

    public String changeMemberReport(Long reportId) {
        MemberReport memberReport = memberReportRepository.findById(reportId).orElse(null);
        if(memberReport == null)
            throw new RuntimeException("없는 유저신고 아이디입니다.");

        Member member = memberRepository.findById(memberReport.getReportedUserId()).orElse(null);
        if(member==null)
            throw new RuntimeException("없는 유저입니다");
        member.setStatus("ben");
        memberRepository.save(member);

        memberReport.setStatus("complete");
        memberReport.setProcessing(true);
        memberReportRepository.save(memberReport);

        return "유저신고가 정상적으로 처리되었습니다.";
    }

    public String changeMaterialReport(Long reportId) {
        MaterialReport materialReport = materialReportRepository.findById(reportId).orElse(null);
        if(materialReport == null)
            throw new RuntimeException("없는 유저신고 아이디입니다.");

        Material material = materialRepository.findById(materialReport.getReportedMaterialId()).orElse(null);
        if(material == null)
            throw new NotFoundException(NOT_PRESENT_MATERIAL);

        material.setStatus("ben");
        materialRepository.save(material);

        materialReport.setStatus("complete");
        materialReport.setProcessing(true);
        materialReportRepository.save(materialReport);
        return "과제신고가 정상적으로 처리되었습니다";
    }
}
