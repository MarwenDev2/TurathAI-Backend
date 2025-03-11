package pi.turathai.turathaibackend.Entites;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private float latitude;
    private float longitude;
    private String contact;

    @ManyToOne
    @JoinColumn(name = "idSite")
    private HeritageSite heritageSite;

    @ManyToMany
    @JoinTable(
            name = "businessImages",
            joinColumns = @JoinColumn(name = "idBusiness"),
            inverseJoinColumns = @JoinColumn(name = "idImage")
    )
    private Set<Image> images;
}
