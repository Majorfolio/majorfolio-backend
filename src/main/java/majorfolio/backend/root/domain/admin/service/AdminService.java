package majorfolio.backend.root.domain.admin.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.admin.dto.request.PostEventRequest;
import majorfolio.backend.root.domain.admin.dto.request.PostNoticeRequest;
import majorfolio.backend.root.domain.admin.dto.response.PostEventResponse;
import majorfolio.backend.root.domain.admin.dto.response.PostNoticeResponse;
import majorfolio.backend.root.domain.admin.entity.Event;
import majorfolio.backend.root.domain.admin.entity.Notice;
import majorfolio.backend.root.domain.admin.repository.EventRepository;
import majorfolio.backend.root.domain.admin.repository.NoticeRepository;
import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.material.repository.MaterialRepository;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.repository.MemberRepository;
import majorfolio.backend.root.domain.report.entity.MaterialReport;
import majorfolio.backend.root.domain.report.entity.MemberReport;
import majorfolio.backend.root.domain.report.repository.MaterialReportRepository;
import majorfolio.backend.root.domain.report.repository.MemberReportRepository;
import majorfolio.backend.root.global.exception.NotFoundException;
import majorfolio.backend.root.global.util.S3Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.NOT_PRESENT_MATERIAL;
import static majorfolio.backend.root.global.status.S3DirectoryEnum.EVENTS3;
import static majorfolio.backend.root.global.status.S3DirectoryEnum.NOTICES3;


@Service
@RequiredArgsConstructor
public class AdminService {
    @Value("${cloud.aws.s3.bucket}")
    private String s3Bucket;
    private final AmazonS3Client amazonS3;
    private final NoticeRepository noticeRepository;
    private final EventRepository eventRepository;
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
        if (materialReport == null)
            throw new RuntimeException("없는 유저신고 아이디입니다.");

        Material material = materialRepository.findById(materialReport.getReportedMaterialId()).orElse(null);
        if (material == null)
            throw new NotFoundException(NOT_PRESENT_MATERIAL);

        material.setStatus("ben");
        materialRepository.save(material);

        materialReport.setStatus("complete");
        materialReport.setProcessing(true);
        materialReportRepository.save(materialReport);
        return "과제신고가 정상적으로 처리되었습니다";
    }


    /**
     * 공지사항 글쓰기 서비스 구현
     * @param postNoticeRequest
     * @return
     */
    public PostNoticeResponse postNotice(PostNoticeRequest postNoticeRequest) throws IOException {
        MultipartFile multipartFile = postNoticeRequest.getFile();
        String fileName = S3Util.generateFileName(multipartFile);
        Notice notice = Notice.builder().build();
        noticeRepository.save(notice);
        String link = uploadS3Image(multipartFile, fileName, notice.getId(), NOTICES3.getS3DirectoryName());

        notice.setTitle(postNoticeRequest.getTitle());
        notice.setLink(link);

        noticeRepository.save(notice);

        return PostNoticeResponse.of(notice.getId());
    }



    /**
     * 이미지 업로드 메소드
     * @param multipartFile
     * @param fileName
     * @param id
     * @param mode
     * @return
     * @throws IOException
     */
    private String uploadS3Image(MultipartFile multipartFile, String fileName, Long id, String mode) throws IOException {
        //메타데이터 생성
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());


        //s3업로드
        String fileDirectory = s3Bucket + "/" + mode + "/" + id;
        try (InputStream imageInputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(
                    new PutObjectRequest(fileDirectory, fileName, imageInputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
        }

        return fileDirectory + "/" + fileName;
    }

    /**
     * 이벤트 작성 서비스 구현
     * @param postEventRequest
     * @return
     * @throws IOException
     */
    public PostEventResponse postEvent(PostEventRequest postEventRequest) throws IOException {
        MultipartFile multipartFile = postEventRequest.getFile();
        String fileName = S3Util.generateFileName(multipartFile);
        Event event = Event.builder().build();
        eventRepository.save(event);
        String link = uploadS3Image(multipartFile, fileName, event.getId(), EVENTS3.getS3DirectoryName());

        event.setTitle(postEventRequest.getTitle());
        event.setLink(link);

        eventRepository.save(event);

        return PostEventResponse.of(event.getId());
    }
}
