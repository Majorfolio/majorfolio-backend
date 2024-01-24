package majorfolio.backend.root.domain.university.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "University")
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
