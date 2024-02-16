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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
public class AssignmentUploadRequest {
    @NotBlank(message = "title : 과제 제목이 비어있으면 안됩니다.")
    private String title;
    @NotBlank(message = "major : 전공이 비어있으면 안됩니다.")
    private String major;
    @NotBlank(message = "semester : 학기정보가 비어있으면 안됩니다.")
    @Pattern(regexp = "\\d{2}-\\d", message = "semester : 학기 정보 형식은 00-0형식이어야 합니다.")
    private String semester;
    @NotBlank(message = "subjectName : 과목명이 비어있으면 안됩니다.")
    private String subjectName;
    @NotBlank(message = "professor : 교수명이 비어있으면 안됩니다.")
    private String professor;
    @Pattern(regexp = "A[+-]?|B[+-]?|C[+-]?|D[+-]?|F", message = "grade : 올바른 학점 형식이어야 합니다.")
    private String grade;
    @NotNull(message = "fullScore : 총점이 비어있으면 안됩니다.")
    private Integer fullScore;
    @NotNull(message = "score : 점수가 비어있으면 안됩니다.")
    private Integer score;
    @NotBlank(message = "description : 설명이 쓰여 있어야 합니다.")
    @Size(max = 80, message = "description : 설명은 80자로 제한됩니다.")
    private String description;

}