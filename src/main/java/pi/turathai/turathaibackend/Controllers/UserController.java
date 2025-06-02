package pi.turathai.turathaibackend.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import pi.turathai.turathaibackend.DTO.UserCountryDTO;
import pi.turathai.turathaibackend.DTO.UserGrowthDTO;
import pi.turathai.turathaibackend.DTO.UserRoleDTO;
import pi.turathai.turathaibackend.Repositories.UserRepository;
import pi.turathai.turathaibackend.Services.IUserService;
import pi.turathai.turathaibackend.Services.IUserPreferencesService;
import pi.turathai.turathaibackend.Entites.User;
import pi.turathai.turathaibackend.Entites.UserPreferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Services.UserStatisticsService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins= "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserStatisticsService userStatisticsService;
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{userId}")
    public Optional<User> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User userDetails) {
        return userService.updateUser(userId, userDetails);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }

    // Add to your UserController.java
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/last-id")
    public ResponseEntity<Long> getLastUserId() {
        User lastUser = userService.getLastCreatedUser();
        if (lastUser != null) {
            return ResponseEntity.ok(lastUser.getId());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/change-password/{userId}")
    public ResponseEntity<?> changePassword(@PathVariable Long userId,
                                            @RequestBody Map<String, String> passwords) {
        Optional<User> optionalUser = userService.getUserById(userId);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(passwords.get("currentPassword"), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Current password is incorrect"));
        }

        userService.changeUserPassword(user, passwords.get("newPassword"));
        return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
    }


    @GetMapping("/growth")
    public List<UserGrowthDTO> getUserGrowth() {
        return userStatisticsService.getUserGrowthStatistics();
    }

    @GetMapping("/by-country")
    public List<UserCountryDTO> getUsersByCountry() {
        return userStatisticsService.getUserCountryStatistics();
    }
    @GetMapping("/by-role")
    public List<UserRoleDTO> getUsersByRole() {
        return userStatisticsService.getUserRoleStatistics();
    }

    @GetMapping("/recent")
    public List<User> getRecentUsers(@RequestParam(defaultValue = "5") int limit) {
        return userStatisticsService.getRecentUsers(limit);
    }
}