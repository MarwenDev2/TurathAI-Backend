package pi.turathai.turathaibackend.Entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pi.turathai.turathaibackend.Entites.HeritageSite;

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


        private Integer likes = 0;
        private Integer dislikes = 0;




    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "idSite")
    private HeritageSite heritageSite;
}
