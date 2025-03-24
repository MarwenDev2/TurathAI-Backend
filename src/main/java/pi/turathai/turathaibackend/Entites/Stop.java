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
    @Id
    private int stopOrder;
    private String duration;

    @ManyToOne
    @JoinColumn(name = "idItinery")
    private Itinery itinery;


    @ManyToOne
    @JoinColumn(name = "idSite")
    private HeritageSite heritageSite;
}
