package majorfolio.backend.root.domain.university.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Subjects")
public class Subjects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;
}
