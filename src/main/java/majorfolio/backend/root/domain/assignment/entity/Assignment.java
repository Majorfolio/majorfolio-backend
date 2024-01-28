package majorfolio.backend.root.domain.assignment.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDateTime deadline;

    private String image_url;

    private Long price;

    private String status;

    private String file_url;




}
