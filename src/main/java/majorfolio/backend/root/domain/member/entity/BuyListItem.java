package majorfolio.backend.root.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.payments.entity.BuyInfo;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BuyListItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buyListItem_id")
    private Long id;

    private Boolean isDown;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @ManyToOne
    @JoinColumn(name = "buyList_id")
    private BuyList buyList;

    @ManyToOne
    @JoinColumn(name = "buyInfo_id")
    private BuyInfo buyInfo;

    public static BuyListItem of(Material material, BuyList buyList){
        return BuyListItem.builder()
                .isDown(false)
                .material(material)
                .buyList(buyList)
                .build();
    }
}
