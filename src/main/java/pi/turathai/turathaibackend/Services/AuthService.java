package pi.turathai.turathaibackend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.DTO.SocialLoginRequest;
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

    private UserDetailsService userDetailsService;

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
    // In AuthService.java
    public String refreshToken(String oldToken) {
        String username = jwtUtil.extractUsername(oldToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!jwtUtil.validateToken(oldToken,userDetails)) {
            throw new RuntimeException("Invalid token");
        }


        if (!jwtUtil.validateToken(oldToken, userDetails)) {
            throw new RuntimeException("Token validation failed");
        }

        return jwtUtil.generateToken(userDetails);
    }

    /**
     * Handle social login from providers like Google and Facebook
     * This method will either create a new user if one doesn't exist with this email
     * or authenticate an existing user
     *
     * @param socialUser The user details from the social provider
     * @return JWT token for authentication
     */
    public String handleSocialLogin(SocialLoginRequest socialUser) {
        try {
            // Check if user exists
            User user = userRepository.findByEmail(socialUser.getEmail());

            if (user == null) {
                // Create a new user with social login details
                user = new User();
                user.setEmail(socialUser.getEmail());
                user.setFirstName(socialUser.getFirstName());
                user.setLastName(socialUser.getLastName());

                // Generate a secure random password (the user won't use this to log in)
                String randomPassword = generateSecurePassword();
                user.setPassword(passwordEncoder.encode(randomPassword));

                // Set default values for required fields
                user.setRole("USER");
                user.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));

                // Set optional fields if available
                if (socialUser.getPhotoUrl() != null && !socialUser.getPhotoUrl().isEmpty()) {
                    user.setImage(socialUser.getPhotoUrl());
                }

                // Set default values for any other required fields
                user.setOriginCountry("Not specified");
                user.setSpokenLanguage("Not specified");
                user.setInterests("Not specified");

                // Save the new user
                user = userRepository.save(user);
                System.out.println("Created new user from social login: " + user.getEmail());
            } else {
                System.out.println("User already exists: " + user.getEmail());
            }

            // Create and return a JWT token for the user
            return jwtUtil.generateToken(user);

        } catch (Exception e) {
            System.out.println("Social authentication error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Social login failed: " + e.getMessage());
        }
    }

    private String generateSecurePassword() {
        // Simple implementation - in production, use a more secure method
        return java.util.UUID.randomUUID().toString();
    }
}
