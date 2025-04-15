package pi.turathai.turathaibackend.Entites;

<<<<<<< HEAD

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
=======
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
>>>>>>> main

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
<<<<<<< HEAD
public class User {
=======
public class User implements UserDetails {
>>>>>>> main

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
<<<<<<< HEAD
    private String email;
    private String password;
    private String role;
    private String originCountry;
    private String SpokenLanguage;
    private String interests;
    private Date createdAt;

}
=======

    @Column(unique = true)
    private String email;

    private String password;
    private String role;
    private String originCountry;
    private String spokenLanguage;
    private String interests;
    private Date createdAt;

    // UserDetails methods - all hidden from JSON
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;  // Using email as username
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
>>>>>>> main
