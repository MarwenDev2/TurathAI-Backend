package pi.turathai.turathaibackend.Entites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;
    private String image;
    private Date createdAt;
    private int liked;
    private int disliked;

    @ManyToOne
    @JoinColumn(name = "idUser")
    @JsonBackReference // ↔ éviter la boucle avec User
    private User user;

    @ManyToOne
    @JoinColumn(name = "idForum")
    @JsonBackReference // ↔ éviter la boucle avec Forum
    private Forum forum;
}
