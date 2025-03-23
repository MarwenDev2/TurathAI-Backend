package pi.turathai.turathaibackend.Entites;

import jakarta.persistence.*;
import lombok.*;


import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idUser", "idSite"}) // Ensure a unique combination of user and heritage site
})
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key

    private int rating;
    private String comment;
    private Date createdAt;
    private boolean flagged;  // Attribute to flag inappropriate reviews

    @PrePersist
    protected void onCreate() {
        createdAt = new Date(System.currentTimeMillis());
    }

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "idSite")
    private HeritageSite heritageSite;
}
