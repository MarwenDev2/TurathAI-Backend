package pi.turathai.turathaibackend.Entites;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocalInsight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String type;
    private String videoURL;
    private String audioURL;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "idSite")
    private HeritageSite heritageSite;


}
