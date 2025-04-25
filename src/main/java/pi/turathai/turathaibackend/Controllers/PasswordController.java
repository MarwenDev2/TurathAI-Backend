package pi.turathai.turathaibackend.Controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import pi.turathai.turathaibackend.DTO.UserDTO;
import pi.turathai.turathaibackend.Entites.User;
import pi.turathai.turathaibackend.Security.EmailService;
import pi.turathai.turathaibackend.Security.PasswordResetTokenService;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Services.UserService;

import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api/auth")
public class PasswordController {

    private final UserService userService;
    private final EmailService emailService;
    private final PasswordResetTokenService tokenService;

    public PasswordController(UserService userService,
                              EmailService emailService,
                              PasswordResetTokenService tokenService) {
        this.userService = userService;
        this.emailService = emailService;
        this.tokenService = tokenService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        try {
            User user = userService.findUserByEmail(email);

            if (user == null) {
                return ResponseEntity.ok().body(Map.of(
                        "message", "If this email exists, a reset link has been sent",
                        "status", "SUCCESS"
                ));
            }
            String newPassword = generateRandomPassword(10);
            userService.changeUserPassword(user, newPassword);
            String token = UUID.randomUUID().toString();
            tokenService.createPasswordResetTokenForUser(user, token);

            emailService.sendPasswordResetEmail(
                    user.getEmail(),
                    user.getFullName(),
                    token,
                    newPassword
            );


            return ResponseEntity.ok().body(Map.of(
                    "message", "Password reset link sent to your email",
                    "status", "SUCCESS"
            ));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", e.getMessage(),
                    "status", "ERROR"
            ));
        }
    }

    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword) {
        System.out.println("Received token: " + token);

        String result = tokenService.validatePasswordResetToken(token);

        if (result != null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", result,
                    "status", "ERROR"
            ));
        }

        User user = tokenService.getUserByPasswordResetToken(token);
        if (user != null) {
            userService.changeUserPassword(user, newPassword);
            return ResponseEntity.ok().body(Map.of(
                    "message", "Password reset successfully",
                    "status", "SUCCESS"
            ));
        } else {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "Invalid token",
                    "status", "ERROR"
            ));
        }
    }
}