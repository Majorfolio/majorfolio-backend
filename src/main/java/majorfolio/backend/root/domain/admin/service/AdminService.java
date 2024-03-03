package majorfolio.backend.root.domain.admin.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.admin.dto.request.PostNoticeRequest;
import majorfolio.backend.root.domain.admin.dto.response.PostNoticeResponse;
import majorfolio.backend.root.domain.admin.entity.Notice;
import majorfolio.backend.root.domain.admin.repository.NoticeRepository;
import majorfolio.backend.root.global.util.S3Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static majorfolio.backend.root.global.status.S3DirectoryEnum.NOTICES3;


@Service
@RequiredArgsConstructor
public class AdminService {
    @Value("${cloud.aws.s3.bucket}")
    private String s3Bucket;
    private final AmazonS3Client amazonS3;

    private final NoticeRepository noticeRepository;

    /**
     * 공지사항 글쓰기 서비스 구현
     * @param postNoticeRequest
     * @return
     */
    public PostNoticeResponse postNotice(PostNoticeRequest postNoticeRequest) throws IOException {
        MultipartFile multipartFile = postNoticeRequest.getFile();
        String fileName = S3Util.generateFileName(multipartFile);
        Notice notice = Notice.builder().build();
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
}
