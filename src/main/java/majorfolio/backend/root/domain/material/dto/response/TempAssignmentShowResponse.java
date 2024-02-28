package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 임시저장함 조회 api 응답 객체
 */
@Getter
@Builder
public class TempAssignmentShowResponse {
    private Long tempMaterialId;
    private String className;
    private String type;
    private String univ;
    private String major;
    private String semester;
    private String professor;

    public static TempAssignmentShowResponse of(
            Long tempMaterialId,
            String className,
            String type,
            String univ,
            String major,
            String semester,
            String professor
    ){
        return TempAssignmentShowResponse.builder()
                .tempMaterialId(tempMaterialId)
                .className(className)
                .type(type)
                .univ(univ)
                .major(major)
                .semester(semester)
                .professor(professor)
                .build();
    }
}
