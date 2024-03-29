/**
 * Majorfolio
 *
 * 2024.02.16
 *
 * 0.0.1
 *
 * AssignmentUploadRequest
 */
package majorfolio.backend.root.domain.material.dto.request;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.*;
import lombok.*;
import majorfolio.backend.root.global.argument_resolver.custom_annotation.ValidFile;
import majorfolio.backend.root.global.argument_resolver.custom_annotation.ValidGrade;
import majorfolio.backend.root.global.argument_resolver.custom_annotation.ValidScore;
import org.springframework.web.multipart.MultipartFile;

/**
 * 자료 업로드 api시 요청 클래스 정의
 *
 * @author 김영록
 * @version 0.0.1
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssignmentUploadRequest {
    @ValidFile(message = "file : 파일이 비어있으면 안됩니다.")
    private MultipartFile file;
    @NotBlank(message = "title : 과제 제목이 비어있으면 안됩니다.")
    private String title;
    @NotBlank(message = "major : 전공이 비어있으면 안됩니다.")
    private String major;
    @NotBlank(message = "semester : 학기정보가 비어있으면 안됩니다.")
    @Pattern(regexp = "\\d{2}-\\d", message = "semester : 학기 정보 형식은 00-0형식이어야 합니다.")
    private String semester;
    @NotBlank(message = "className : 과목명이 비어있으면 안됩니다.")
    private String className;
    private String professor;
    @ValidGrade(message = "grade : 올바른 학점 형식이어야 합니다.")
    private String grade;
    @ValidScore(message = "fullScore : 만점은 소수점 첫째 자리까지 입력하셔야 합니다.")
    private Float fullScore;
    @ValidScore(message = "score : 점수는 소수점 첫째 자리까지 입력하셔야 합니다.")
    private Float score;
    @NotBlank(message = "description : 설명이 쓰여 있어야 합니다.")
    @Size(max = 80, message = "description : 설명은 10자 이상 80자이내로 제한됩니다.")
    private String description;

}
