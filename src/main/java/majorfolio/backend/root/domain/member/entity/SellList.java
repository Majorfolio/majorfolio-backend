package majorfolio.backend.root.domain.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SellList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sellList_id")
    private Long id;
}
