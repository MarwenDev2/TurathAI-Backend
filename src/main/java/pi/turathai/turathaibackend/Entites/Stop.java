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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "`order`")
    private int order;
    private String duration;

    @ManyToOne
    @JoinColumn(name = "idItinery")
    private Itinery itinery;

    @ManyToOne
    @JoinColumn(name = "idSite")
    private HeritageSite heritageSite;
}
