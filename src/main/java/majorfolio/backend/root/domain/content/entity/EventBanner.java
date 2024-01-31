package majorfolio.backend.root.domain.content.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * EventBanner의 테이블 정의
 * @author 김태혁
 * @version 0.0.1
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventBanner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contents_id")
    private Long id;
    private String imageURL;
    private String type;
    private String backgroundColor;
    private String iconURL;

    /**
     * EventBanner의 데이터 생성
     * @author 김태혁
     * @version 0.0.1
     */
    public static EventBanner of(String imageURL, String type, String backgroundColor, String iconURL){
        return EventBanner.builder()
                .imageURL(imageURL)
                .type(type)
                .backgroundColor(backgroundColor)
                .iconURL(iconURL)
                .build();
    }
}
