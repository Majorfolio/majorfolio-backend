package majorfolio.backend.root.domain.report.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class MemberReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_report_id")
    private Long reporterId;
    private Long reportedUserId;
    private String reason;
    private String description;
    private boolean processing;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private String status;

    public static MemberReport of(Long reporterId, Long reportedUserId, String reason,
                                  String description){
        return MemberReport.builder()
                .reporterId(reporterId)
                .reportedUserId(reportedUserId)
                .reason(reason)
                .description(description)
                .processing(false)
                .status("accepted")
                .build();
    }

}
