package pi.turathai.turathaibackend.Entites;

import jakarta.persistence.*;
import lombok.*;

<<<<<<< HEAD
import java.sql.Date;
=======
<<<<<<< HEAD
import java.sql.Date;
import java.util.List;
=======
import java.time.LocalDateTime;
>>>>>>> a07ebc221fe59784c111634b5e124c10d13a4ec9
>>>>>>> main

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String image;
<<<<<<< HEAD
    private Date createdAt;
=======

    private LocalDateTime createdAt; // ✅ Correct
>>>>>>> main

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;
<<<<<<< HEAD
=======

    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

>>>>>>> main
}
