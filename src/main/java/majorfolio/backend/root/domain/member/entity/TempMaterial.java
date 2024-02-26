package majorfolio.backend.root.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import majorfolio.backend.root.domain.material.entity.Material;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TempMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tempMaterial_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tempStorage_id")
    private TempStorage tempStorage;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;
}
