package pi.turathai.turathaibackend.Entites;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

<<<<<<< HEAD
@Entity
=======
/**
 * Wishlist entity representing a user's saved heritage sites.
 * Ensures uniqueness per user-heritage site pair.
 */

@Entity
@Table(name = "wishlist", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idUser", "idSite"}) //This ensures that a user cannot add the same heritage site multiple times to their wishlist : no duplicates entries
})
>>>>>>> main
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Wishlist {
<<<<<<< HEAD

    private Date createdAt;

    @Id
    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "idSite")
=======
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "idSite", nullable = false)
>>>>>>> main
    private HeritageSite heritageSite;
}
