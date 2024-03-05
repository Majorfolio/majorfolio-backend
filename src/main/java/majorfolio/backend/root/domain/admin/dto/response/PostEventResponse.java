package majorfolio.backend.root.domain.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostEventResponse {
    private Long eventId;

    public static PostEventResponse of(
            Long eventId
    ){
        return PostEventResponse.builder()
                .eventId(eventId)
                .build();
    }
}
