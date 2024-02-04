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

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.material.dto.response.MaterialResponse;
import majorfolio.backend.root.domain.material.dto.response.assignment.MaterialDetailResponse;
import majorfolio.backend.root.domain.material.service.AssignmentService;
import majorfolio.backend.root.global.CustomMultipartFile;
import majorfolio.backend.root.global.response.BaseResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
@RequiredArgsConstructor
@Slf4j
public class AssignmentController {
    @Value("${cloud.aws.s3.bucket}")
    private String s3Bucket;  // application.properties에서 S3 버킷 설정

    private final AmazonS3Client amazonS3;

    private final AssignmentService assignmentService;

    /**
     * 과제 상세페이지(구매자 입장) API
     * @param materialId
     * @return
     */
    @GetMapping("/{materialId}/detail")
    public BaseResponse<MaterialDetailResponse> showDetailMaterial(@PathVariable(name = "materialId")
                                                             Long materialId){
        return new BaseResponse<>(assignmentService.showDetailMaterial(materialId));
    }

//    /**
//     * S3 버킷 연동 테스트 용(나중에 서비스랑 분리 예정)
//     * @param pdfFile
//     * @return
//     * @throws IOException
//     */
//    @PutMapping("/upload")
//    public BaseResponse<String> S3test(@RequestPart("file")MultipartFile pdfFile) throws IOException {
//        List<String> imageUrlList = null;
//
//        PDDocument document = PDDocument.load(pdfFile.getBytes());
//        PDFRenderer pdfRenderer = new PDFRenderer(document);
//        BufferedImage imageObj = pdfRenderer.renderImageWithDPI(0, 100, ImageType.RGB);
//        MultipartFile image = convertBufferedImageToMultipartFile(imageObj);
//        log.info(image.toString());
//        String fileName = generateFileName(image);
//        log.info(fileName);
//        String fileUrl= "https://" + s3Bucket + "/test" +fileName;
//        ObjectMetadata metadata= new ObjectMetadata();
//        metadata.setContentType(image.getContentType());
//        metadata.setContentLength(image.getSize());
//
//        try (InputStream imageInputStream = image.getInputStream()) {
//            amazonS3.putObject(
//                    new PutObjectRequest(s3Bucket, fileName, imageInputStream, metadata)
//                            .withCannedAcl(CannedAccessControlList.PublicRead));
//        }
//
//        return new BaseResponse<>(fileUrl);
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





}
