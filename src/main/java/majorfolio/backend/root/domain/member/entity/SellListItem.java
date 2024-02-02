package majorfolio.backend.root.domain.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import majorfolio.backend.root.domain.material.entity.Material;

import java.time.LocalDateTime;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SellListItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sellListItem_id")
    private Long id;

    private String status;
    private LocalDateTime date;
    private Long buyer;

    @ManyToOne
    @JoinColumn(name = "sellList_id")
    private SellList sellList;

    @OneToOne
    @JoinColumn(name = "material_id")
    private Material material;
}
