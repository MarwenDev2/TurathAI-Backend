package pi.turathai.turathaibackend.Entites;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String preferenceCategories;
    private String travelStyles;
    private String budgetRange;
    private String languagePreferences;

    @OneToOne
    @JoinColumn(name = "idUser")
    private User user;
}
