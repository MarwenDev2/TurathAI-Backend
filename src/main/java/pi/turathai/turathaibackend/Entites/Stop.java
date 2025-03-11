package pi.turathai.turathaibackend.Entites;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Stop {

    private int order;
    private String duration;

    @Id
    @ManyToOne
    @JoinColumn(name = "idItinery")
    private Itinery itinery;

    @Id
    @ManyToOne
    @JoinColumn(name = "idSite")
    private HeritageSite heritageSite;
}
