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

    private int rating;
    private String comment;
    private Date createdAt;

    @Id
    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "idSite")
    private HeritageSite heritageSite;

}
