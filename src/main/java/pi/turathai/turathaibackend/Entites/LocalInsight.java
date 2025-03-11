package pi.turathai.turathaibackend.Entites;
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
    @JoinColumn(name = "idSite")
    private HeritageSite heritageSite;

}
