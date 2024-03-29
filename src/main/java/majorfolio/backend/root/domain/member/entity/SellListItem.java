/**
 * SellListItem
 *
 * 2024.02.04
 *
 * 0.0.1
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import majorfolio.backend.root.domain.material.entity.Material;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * SellListItem Entity 객체
 *
 * @author 김영록
 * @version 0.0.1
 */
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class SellListItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sellListItem_id")
    private Long id;

    private String status;
    private LocalDateTime date;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private Long buyer;

    @ManyToOne
    @JoinColumn(name = "sellList_id")
    private SellList sellList;

    // One To One으로 하니깐 DB에 데이터를 넣을때 중복이 안됨(구매자 여러명이 이 과제를 샀을때를 구현 못함)
    // 그래서 관계를 떼도 material Id로 해야할듯
//    @OneToOne
//    @JoinColumn(name = "material_id")
    private Long materialId;

    /**
     * SellListItem 생성 메서드
     * @param date
     * @param buyer
     * @param sellList
     * @param materialId
     * @return
     */
    public static SellListItem of(LocalDateTime date, Long buyer, SellList sellList,
                                  Long materialId){
        return SellListItem.builder()
                .status("pending")
                .date(date)
                .buyer(buyer)
                .sellList(sellList)
                .materialId(materialId)
                .build();
    }
}
