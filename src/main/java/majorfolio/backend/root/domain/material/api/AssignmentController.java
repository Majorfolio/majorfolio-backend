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
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.material.dto.request.AssignmentUploadRequest;
import majorfolio.backend.root.domain.material.dto.response.assignment.*;
import majorfolio.backend.root.domain.material.dto.request.TempAssignmentModifyRequest;
import majorfolio.backend.root.domain.material.dto.request.TempAssignmentSaveRequest;
import majorfolio.backend.root.domain.material.dto.response.TempAssignmentDetailResponse;
import majorfolio.backend.root.domain.material.dto.response.TempAssignmentShowResponse;
import majorfolio.backend.root.domain.material.dto.response.assignment.AssignmentDownloadResponse;
import majorfolio.backend.root.domain.material.dto.response.assignment.AssignmentUploadResponse;
import majorfolio.backend.root.domain.material.dto.response.assignment.MaterialDetailResponse;
import majorfolio.backend.root.domain.material.dto.response.assignment.MaterialMyDetailResponse;
import majorfolio.backend.root.domain.material.dto.response.assignment.stat.MaterialStatsResponse;
import majorfolio.backend.root.domain.material.service.AssignmentService;
import majorfolio.backend.root.global.argument_resolver.TokenInformation;
import majorfolio.backend.root.global.argument_resolver.custom_annotation.TokenInfo;
import majorfolio.backend.root.global.response.BaseResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

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
                                                                   @TokenInfo Long binderMemberId) throws InvalidKeySpecException, IOException {
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
     * @param assignmentUploadRequest
     * @param servletRequest
     * @return
     * @throws IOException
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

    @GetMapping("/{materialId}/previews")
    public BaseResponse<PreviewResponse> showPreview(@PathVariable(name = "materialId") Long materialId) throws InvalidKeySpecException, IOException {
        return new BaseResponse<>(assignmentService.showPreview(materialId));
    }

    /**
     * 임시보관함 저장
     * @param tempAssignmentSaveRequest
     * @param memberId
     * @return
     * @throws IOException
     */
    @PostMapping("/temp/save")
    public BaseResponse<String> saveTemporarily(@Validated @ModelAttribute TempAssignmentSaveRequest tempAssignmentSaveRequest,
                                                @TokenInfo Long memberId) throws IOException {
        return new BaseResponse<>(assignmentService.saveTemporarily(memberId, tempAssignmentSaveRequest));
    }

    /**
     * 임시보관함 수정
     * @param tempAssignmentModifyRequest
     * @param memberId
     * @param tempMaterialId
     * @return
     * @throws IOException
     */
    @PatchMapping("/temp/{tempMaterialId}")
    public BaseResponse<String> modifyTemporarily(@Validated @ModelAttribute TempAssignmentModifyRequest tempAssignmentModifyRequest,
                                                  @TokenInfo Long memberId, @PathVariable(name = "tempMaterialId") Long tempMaterialId) throws IOException {
        return new BaseResponse<>(assignmentService.modifyTempMaterial(memberId, tempMaterialId, tempAssignmentModifyRequest));
    }

    /**
     * 임시보관함 조회 API
     * @param memberId
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/temp")
    public BaseResponse<List<TempAssignmentShowResponse>> showTempStorage(@TokenInfo Long memberId,
                                                                          @RequestParam(name = "page") int page,
                                                                          @RequestParam(name = "pageSize", defaultValue = "5") int pageSize){
        return new BaseResponse<>(assignmentService.showTempStorage(memberId, page, pageSize));
    }

    /**
     * 임시보관함 상세 조회 API
     * @param memberId
     * @param tempMaterialId
     * @return
     * @throws InvalidKeySpecException
     * @throws IOException
     */
    @GetMapping("/temp/{tempMaterialId}")
    public BaseResponse<TempAssignmentDetailResponse> showTempMaterialDetail(@TokenInfo Long memberId,
                                                                             @PathVariable(name = "tempMaterialId") Long tempMaterialId) throws InvalidKeySpecException, IOException {
        return new BaseResponse<>(assignmentService.showTempMaterialDetail(memberId, tempMaterialId));
    }

    /**
     * 임시보관함 객체 삭제 API
     * @param memberId
     * @param tempMaterialId
     * @return
     */
    @DeleteMapping("/temp/{tempMaterialId}")
    public BaseResponse<String> deleteTempMaterial(@TokenInfo Long memberId,
                                                   @PathVariable(name = "tempMaterialId") Long tempMaterialId){
        return new BaseResponse<>(assignmentService.deleteTempMaterial(memberId, tempMaterialId));
    }

    /**
     * 자료 수정 API
     * @param memberId
     * @param materialId
     * @param assignmentUploadRequest
     * @return
     * @throws IOException
     */
    @PatchMapping("/{materialId}")
    public BaseResponse<AssignmentUploadResponse> modifyAssignment(@Validated @ModelAttribute AssignmentUploadRequest assignmentUploadRequest,
                                                                    @TokenInfo Long memberId,
                                                                    @PathVariable(name = "materialId") Long materialId) throws IOException {
        return new BaseResponse<>(assignmentService.modifyAssignment(memberId, materialId, assignmentUploadRequest));
    }

}
