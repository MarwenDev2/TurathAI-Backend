package pi.turathai.turathaibackend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.DTO.UserDTO;
import pi.turathai.turathaibackend.Entites.User;
import pi.turathai.turathaibackend.Repositories.UserRepository;
import pi.turathai.turathaibackend.Security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public User registerUser(UserDTO userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setOriginCountry(userDto.getOriginCountry());
        user.setSpokenLanguage(userDto.getSpokenLanguage());
        user.setInterests(userDto.getInterests());
        user.setRole("USER");
        user.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));
        user.setImage(userDto.getImage());

        return userRepository.save(user);
    }

    public String loginUser(String email, String password) {
        try {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new RuntimeException("User not found");
            }

            System.out.println("User found: " + user.getEmail());
            System.out.println("Password matches: " + passwordEncoder.matches(password, user.getPassword()));
            System.out.println("User authorities: " + user.getAuthorities());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Get the email from authentication and fetch your User entity
            String authenticatedEmail = authentication.getName();
            User authenticatedUser = userRepository.findByEmail(authenticatedEmail);

            System.out.println("Authenticated user: " + authenticatedUser.getEmail());

            return jwtUtil.generateToken(authenticatedUser);
        } catch (Exception e) {
            System.out.println("Authentication error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Invalid email or password");
        }
    }

    public boolean validateToken(String token) {
        try {
            String email = jwtUtil.extractUsername(token);
            User user = userRepository.findByEmail(email);
            return jwtUtil.validateToken(token, user);
        } catch (Exception e) {
            return false;
        }
    }
}
