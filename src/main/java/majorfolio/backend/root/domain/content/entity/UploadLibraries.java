package majorfolio.backend.root.domain.content.entity;


import jakarta.persistence.*;
@Entity
@Table(name = "UploadLibraries")
public class UploadLibraries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

}