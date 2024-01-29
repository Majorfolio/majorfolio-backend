package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MaterialListResponse {
    private final List<MaterialResponse> newUpload;
    private final List<MaterialResponse> best;
    private final List<MaterialResponse> latest;

    public static MaterialListResponse of(List<MaterialResponse> newUpload,
                                          List<MaterialResponse> best,
                                          List<MaterialResponse> latest){
        return MaterialListResponse.builder()
                .newUpload(newUpload)
                .best(best)
                .latest(latest)
                .build();
    }
}