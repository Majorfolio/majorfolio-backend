package majorfolio.backend.root.domain.material.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Preview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "preview_id")
    private Long id;

    private String image1;
    private String image2;
    private String image3;
}
