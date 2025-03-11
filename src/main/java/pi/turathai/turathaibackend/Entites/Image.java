package pi.turathai.turathaibackend.Entites;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String url;
    private String type;

    @ManyToMany(mappedBy = "images")
    private Set<Event> events;

    @ManyToMany(mappedBy = "images")
    private Set<HeritageSite> heritageSites;

    @ManyToMany(mappedBy = "images")
    private Set<Business> businesses;

}
