package pi.turathai.turathaibackend.Controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import pi.turathai.turathaibackend.DTO.SocialLoginRequest;
import pi.turathai.turathaibackend.DTO.UserDTO;
import pi.turathai.turathaibackend.Entites.User;
import pi.turathai.turathaibackend.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Services.UserService;

import java.util.Date;
import java.util.Map;

@CrossOrigin(origins= "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDto) {
        try {
            User registeredUser = authService.registerUser(userDto);
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            String token = authService.loginUser(user.getEmail(), user.getPassword());
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        try {
            boolean isValid = authService.validateToken(token.replace("Bearer ", ""));
            return ResponseEntity.ok().body(Map.of("valid", isValid));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String token) {
        try {
            String newToken = authService.refreshToken(token.replace("Bearer ", ""));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("token", newToken));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    @PostMapping("/social-login")
    public ResponseEntity<?> socialLogin(@RequestBody SocialLoginRequest socialUser) {
        try {
            // Call the service method to handle social login
            String token = authService.handleSocialLogin(socialUser);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}