package majorfolio.backend.root.domain.assignment.entity;


import jakarta.persistence.*;
import lombok.*;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.university.entity.Subjects;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "Assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long id;
    private String title;
    private String description;
    private String image_url;
    private Long price;
    private String status;
    private String file_url;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;
    @OneToOne
    @JoinColumn(name="subject_id")
    private Subjects subjects;
    private String semester;
    private String grade;
    private String score;
    private String pages;
    private Boolean copyright;
    private LocalDateTime viewTimestamp;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdTime;

    public static Assignment of(String title, String description, Long price, String status,
                                Member member, String image_url, String file_url, Subjects subjects,
                                String semester){
        return Assignment.builder()
                .title(title)
                .description(description)
                .price(price)
                .status(status)
                .member(member)
                .image_url(image_url)
                .file_url(file_url)
                .subjects(subjects)
                .semester(semester)
                .copyright(true)
                .viewTimestamp(null)
                .build();
    }



}
