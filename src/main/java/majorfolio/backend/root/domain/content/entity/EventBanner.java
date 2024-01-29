package majorfolio.backend.root.domain.content.entity;

import jakarta.persistence.*;
import lombok.*;

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

    public static EventBanner of(String imageURL, String type, String backgroundColor, String iconURL){
        return EventBanner.builder()
                .imageURL(imageURL)
                .type(type)
                .backgroundColor(backgroundColor)
                .iconURL(iconURL)
                .build();
    }
}
