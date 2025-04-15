package pi.turathai.turathaibackend.Entites;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Earned {

<<<<<<< HEAD
    private Date earnedAt;

    @Id
=======
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date earnedAt;

>>>>>>> main
    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

<<<<<<< HEAD
    @Id
=======
>>>>>>> main
    @ManyToOne
    @JoinColumn(name = "idBadge")
    private Badge badge;
}
