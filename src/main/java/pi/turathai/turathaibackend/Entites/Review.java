package pi.turathai.turathaibackend.Entites;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {

<<<<<<< HEAD
    private int rating;
    private String comment;
    private Date createdAt;

    @Id
=======
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int rating;
    private String comment;
    private Date createdAt;
    private boolean flagged;  // Attribute to flag inappropriate reviews

>>>>>>> main
    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

<<<<<<< HEAD
    @Id
=======
>>>>>>> main
    @ManyToOne
    @JoinColumn(name = "idSite")
    private HeritageSite heritageSite;

<<<<<<< HEAD
=======

    @PrePersist
    protected void onCreate() {
        createdAt = new Date(System.currentTimeMillis());
    }
>>>>>>> main
}
