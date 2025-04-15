package pi.turathai.turathaibackend.Entites;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

<<<<<<< HEAD
=======
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

>>>>>>> main
    private String content;
    private String image;
    private Date createdAt;
    private int liked;
    private int disliked;

<<<<<<< HEAD
    @Id
=======
>>>>>>> main
    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

<<<<<<< HEAD
    @Id
=======
>>>>>>> main
    @ManyToOne
    @JoinColumn(name = "idForum")
    private Forum forum;
}
