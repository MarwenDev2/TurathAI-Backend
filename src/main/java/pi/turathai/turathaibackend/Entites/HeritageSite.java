package pi.turathai.turathaibackend.Entites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HeritageSite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private String description;
    private String historicalSignificance;
    private int popularityScore;

    @OneToMany(mappedBy = "heritageSite", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews;

    @OneToMany(mappedBy = "heritageSite", cascade = CascadeType.ALL)
    private List<LocalInsight> localInsights;

    @ManyToOne
    @JoinColumn(name = "idCategory")

    private Category category;

    @ManyToMany
    @JoinTable(
            name = "heritageSteImages",
            joinColumns = @JoinColumn(name = "idSite"),
            inverseJoinColumns = @JoinColumn(name = "idImage")
    )
    private Set<Image> images;
}