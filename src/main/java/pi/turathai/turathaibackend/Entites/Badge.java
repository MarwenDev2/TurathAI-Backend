package pi.turathai.turathaibackend.Entites;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;

<<<<<<< HEAD
    @OneToMany(mappedBy = "badge", cascade = CascadeType.ALL)
=======
    @OneToMany(mappedBy = "badge", cascade = CascadeType.ALL, orphanRemoval = true)
    //orphanRemoval = true → Deletes earned records when a badge is deleted
>>>>>>> main
    private List<Earned> earnedBadges;

}