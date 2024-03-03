package majorfolio.backend.root.domain.report.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MaterialReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_report_id")
    private Long reporterId;
    private Long reportedMaterialId;
    private String reason;
    private String description;
    private boolean processing;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private String status;

    public static MaterialReport of(Long reporterId, Long reportedMaterialId, String reason,
                                    String description){
        return MaterialReport.builder()
                .reporterId(reporterId)
                .reportedMaterialId(reportedMaterialId)
                .reason(reason)
                .description(description)
                .processing(false)
                .status("received")
                .build();
    }

}
