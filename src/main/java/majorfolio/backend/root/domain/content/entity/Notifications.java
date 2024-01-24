package majorfolio.backend.root.domain.content.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import majorfolio.backend.root.domain.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@Table(name = "Notifications")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private String message;
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    private LocalDateTime timestamp;

    public static Notifications of(String type, String message, String status,
                                   Member member, LocalDateTime timestamp){
        return Notifications.builder()
                .type(type)
                .message(message)
                .status(status)
                .member(member)
                .timestamp(timestamp)
                .build();
    }
}