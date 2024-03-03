package majorfolio.backend.root.domain.admin.entity;

import jakarta.persistence.*;
import lombok.*;
import majorfolio.backend.root.domain.member.entity.Member;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
