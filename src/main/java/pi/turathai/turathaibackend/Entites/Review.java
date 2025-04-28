package pi.turathai.turathaibackend.Entites;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int rating;
    private String comment;
    private Date createdAt;
    private boolean flagged;  // Attribute to flag inappropriate reviews

    @ManyToOne
    @JoinColumn(name = "idUser")
    @JsonIgnoreProperties("reviews")
    private User user;

    @ManyToOne
    @JoinColumn(name = "idSite")
    @JsonIgnoreProperties({"reviews", "localInsights", "category", "images"})
    private HeritageSite heritageSite;


    @PrePersist
    protected void onCreate() {
        createdAt = new Date(System.currentTimeMillis());
    }
}
