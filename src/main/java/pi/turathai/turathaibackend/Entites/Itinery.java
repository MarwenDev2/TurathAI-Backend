package pi.turathai.turathaibackend.Entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Itinery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Date startDate;
    private Date endDate;
    private int budget;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @JsonIgnore
    @OneToMany(mappedBy = "itinery", cascade = CascadeType.ALL)
    private List<Stop> stops; // <-- Add this to navigate from Itinery to Stops
}
