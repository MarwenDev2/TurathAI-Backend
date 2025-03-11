package pi.turathai.turathaibackend.Entites;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private String originCountry;
    private String SpokenLanguage;
    private String interests;
    private Date createdAt;

}
