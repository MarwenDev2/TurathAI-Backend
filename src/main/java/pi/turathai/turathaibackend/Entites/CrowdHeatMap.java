package pi.turathai.turathaibackend.Entites;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CrowdHeatMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long heatmapId;

    private String crowdDensity;
    private String sentimentScore;
    private Date timestamp;

    @OneToOne
    @JoinColumn(name = "idSite")
    private HeritageSite heritageSite;

}
