package pi.turathai.turathaibackend.Entites;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String location;


    @ManyToOne
    @JoinColumn(name = "idSite")
    private HeritageSite heritageSite;

    @ManyToMany
    @JoinTable(
            name = "eventImages",
            joinColumns = @JoinColumn(name = "idEvent"),
            inverseJoinColumns = @JoinColumn(name = "idImage")
    )
    private Set<Image> images;
}
