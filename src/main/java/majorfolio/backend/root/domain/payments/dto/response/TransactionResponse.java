package majorfolio.backend.root.domain.payments.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import majorfolio.backend.root.domain.material.entity.Material;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TransactionResponse {
    private Long id;
    private String className;
    private String major;
    private String univ;
    private int price;
    LocalDate updateAt;
    private Long buyInfoId;

    public static TransactionResponse of(Material material, LocalDateTime updateAt, Long buyInfoId){
        return TransactionResponse.builder()
                .id(material.getId())
                .className(material.getClassName())
                .major(material.getMajor())
                .univ(material.getMember().getUniversityName())
                .price(material.getPrice())
                .updateAt(updateAt.toLocalDate())
                .buyInfoId(buyInfoId)
                .build();
    }

    public static TransactionResponse saleOf(Material material, LocalDateTime updateAt){
        return TransactionResponse.builder()
                .id(material.getId())
                .className(material.getClassName())
                .major(material.getMajor())
                .univ(material.getMember().getUniversityName())
                .price(material.getPrice())
                .updateAt(updateAt.toLocalDate())
                .build();
    }
}
