/**
 * AssignmentController
 *
 * 2024.02.04
 *
 * 0.0.1
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.material.api;

import com.amazonaws.Protocol;
import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.internal.ServiceUtils;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.DateUtils;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.material.dto.request.AssignmentUploadRequest;
import majorfolio.backend.root.domain.material.dto.response.assignment.AssignmentDownloadResponse;
import majorfolio.backend.root.domain.material.dto.response.assignment.AssignmentUploadResponse;
import majorfolio.backend.root.domain.material.dto.response.assignment.MaterialDetailResponse;
import majorfolio.backend.root.domain.material.dto.response.assignment.MaterialMyDetailResponse;
import majorfolio.backend.root.domain.material.dto.response.assignment.stat.MaterialStatsResponse;
import majorfolio.backend.root.domain.material.service.AssignmentService;
import majorfolio.backend.root.global.CustomMultipartFile;
import majorfolio.backend.root.global.argument_resolver.custom_annotation.MemberInfo;
import majorfolio.backend.root.global.response.BaseResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * assignment/* 로 오는 url요청 컨트롤러
 *
 * @author 김영록
 * @version 0.0.1
 */
@RestController
@RequestMapping("/assignment")
//@CrossOrigin(originPatterns = {"http://localhost:3000", "https://majorfolio.github.io/majorfolio-frontend", "https://majorfolio-frontend.vercel.app"})
@RequiredArgsConstructor
@Slf4j
public class AssignmentController {
    @Value("${cloud.aws.s3.bucket}")
    private String s3Bucket;  // application.properties에서 S3 버킷 설정

    private final AmazonS3Client amazonS3;

    private final AssignmentService assignmentService;

    //커밋전 위 변수들 yml파일로 옮겨놓기!


    @Value("${cloud.aws.cloudFront.distributionDomain}")
    private String distributionDomain;

    @Value("${cloud.aws.path}")
    private String privateKeyFilePath;

    @Value("${cloud.aws.cloudFront.keyPairId}")
    private String keyPairId;

    /**
     * 과제 상세페이지(구매자 입장) API
     * @param materialId
     * @return
     */
    @GetMapping("/{materialId}/detail")
    public BaseResponse<MaterialDetailResponse> showDetailMaterial(@PathVariable(name = "materialId")
                                                             Long materialId,
                                                                   @MemberInfo Long binderMemberId) throws InvalidKeySpecException, IOException {
        return new BaseResponse<>(assignmentService.showDetailMaterial(materialId, binderMemberId));
    }

    /**
     * 과제 상세페이지(판매자 입장) API
     * @param materialId
     * @param request
     * @return
     */
    @GetMapping("/my/{materialId}/detail/info")
    public BaseResponse<MaterialMyDetailResponse> showMyDetailMaterial(@PathVariable(name = "materialId")
                                                                       Long materialId,
                                                                       HttpServletRequest request) throws InvalidKeySpecException, IOException {
        Long kakaoId = Long.parseLong(request.getAttribute("kakaoId").toString());
        return new BaseResponse<>(assignmentService.showMyDetailMaterial(kakaoId, materialId));
    }

    /**
     * 과제 상세페이지 통계 API
     * @param materialId
     * @return
     */
    @GetMapping("/my/{materialId}/detail/stats")
    public BaseResponse<MaterialStatsResponse> showMaterialStat(@PathVariable(name = "materialId")
                                                                Long materialId,
                                                                HttpServletRequest request){
        Long kakaoId = Long.parseLong(request.getAttribute("kakaoId").toString());
        return new BaseResponse<>(assignmentService.getMaterialStats(kakaoId, materialId));
    }

    /**
     * 과제 파일 업로드 API
     * @param pdfFile
     * @param assignmentUploadRequest
     * @return
     */
    @PutMapping("/upload")
    public BaseResponse<AssignmentUploadResponse> upload(@Validated @ModelAttribute AssignmentUploadRequest assignmentUploadRequest,
                                                         ServletRequest servletRequest) throws IOException {
        Long kakaoId = Long.parseLong(servletRequest.getAttribute("kakaoId").toString());
        return new BaseResponse<>(assignmentService.uploadPdfFile(kakaoId, assignmentUploadRequest));
    }

    @GetMapping("/download/{id}")
    public BaseResponse<AssignmentDownloadResponse> download(@PathVariable(name = "id") Long materialId,
                                                             ServletRequest servletRequest) throws InvalidKeySpecException, IOException {
        Long memberId = Long.parseLong(servletRequest.getAttribute("memberId").toString());
        return new BaseResponse<>(assignmentService.downloadPdfFile(materialId, memberId));
    }


//    /**
//     * 파일 조회 테스트 용
//     * @param file
//     * @return
//     */
//
//    @GetMapping("/s3/get")
//    public BaseResponse<String> s3Get() throws InvalidKeySpecException, IOException {
//        String fileName = "majorfolio/dced1ead-2d0c-46a3-9aa3-8f202df186bf-73828535-67c6-4c95-92c0-68048f7fd1dc-Activity_8_Overall_Architecture_1+(2).pdf-3.jpeg";
//        String signedUrl = getSignedURLWithCannedPolicy(fileName);
//        return new BaseResponse<>(signedUrl);
//    }
//
//    private ObjectMetadata getObjectMetadata(MultipartFile file) {
//        ObjectMetadata objectMetadata = new ObjectMetadata();
//        objectMetadata.setContentType(file.getContentType());
//        objectMetadata.setContentLength(file.getSize());
//        return objectMetadata;
//    }
//
//    private String generateFileName(MultipartFile file) {
//        return UUID.randomUUID() + "-" + file.getOriginalFilename();
//    }
//
//    private MultipartFile convertBufferedImageToMultipartFile(BufferedImage image) {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        try {
//            ImageIO.write(image, "jpeg", out);
//        } catch (IOException e) {
//            log.error("IO Error", e);
//            return null;
//        }
//        byte[] bytes = out.toByteArray();
//        return new CustomMultipartFile(bytes, "image", "image.jpeg", "jpeg", bytes.length);
//    }
//
//
//    public String getSignedURLWithCannedPolicy( String fileName ) throws InvalidKeySpecException,
//            IOException {
//        File privateKeyFile = new File(privateKeyFilePath);
//        String signedURL = "";
//
//       String policyResourcePath = "https://" + distributionDomain + "/" + fileName;
//
//       Date dateLessThan = ServiceUtils.parseIso8601Date("2024-02-17T00:00:00.00Z");
//
//       String policy = CloudFrontUrlSigner.buildCustomPolicyForSignedUrl(
//               policyResourcePath,
//               dateLessThan,
//               "0.0.0.0/0",
//               null
//
//       );
//
//       PrivateKey privateKey = SignerUtils.loadPrivateKey(privateKeyFile);
//
//       signedURL = CloudFrontUrlSigner.getSignedURLWithCustomPolicy(
//                // Resource URL or Path
//                policyResourcePath,
//                // Certificate identifier, an active trusted signer for the distribution
//                keyPairId,
//                // DER Private key data
//                privateKey,
//                // Access control policy
//                policy
//        );
//        log.info(signedURL);
//        return signedURL;
//    }

}
