package pi.turathai.turathaibackend.Controllers;

import pi.turathai.turathaibackend.Repositories.UserRepository;
import pi.turathai.turathaibackend.Services.IUserPreferencesService;
import pi.turathai.turathaibackend.Entites.UserPreferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins= "http://Localhost:4200")
@RestController
@RequestMapping("/api/user-preferences")
public class UserPreferencesController {

    @Autowired
    private IUserPreferencesService userPreferencesService;
    private UserRepository userRepository;

    @PostMapping
    public UserPreferences createUserPreferences(@RequestBody UserPreferences preferences) {
        if (!userRepository.existsById(preferences.getUser().getId())) {
            throw new RuntimeException("User not found with id: " + preferences.getUser().getId());
        }
        return userPreferencesService.createUserPreferences(preferences);
    }

    @GetMapping("/{preferenceId}")
    public Optional<UserPreferences> getUserPreferencesById(@PathVariable Long preferenceId) {
        return userPreferencesService.getUserPreferencesById(preferenceId);
    }

    @PutMapping("/{preferenceId}")
    public UserPreferences updateUserPreferences(@PathVariable Long preferenceId, @RequestBody UserPreferences preferencesDetails) {
        // Fetch the existing preferences from the database
        UserPreferences existingPreferences = userPreferencesService.getUserPreferencesById(preferenceId)
                .orElseThrow(() -> new RuntimeException("UserPreferences not found with id: " + preferenceId));

        preferencesDetails.setId(preferenceId);

        // Save the updated preferences
        return userPreferencesService.createUserPreferences(preferencesDetails);
    }


    @DeleteMapping("/{preferenceId}")
    public void deleteUserPreferences(@PathVariable Long preferenceId) {
        userPreferencesService.deleteUserPreferences(preferenceId);
    }

    @GetMapping("/user/{userId}")
    public UserPreferences getUserPreferencesByUserId(@PathVariable Long userId) {
        return userPreferencesService.findByUserId(userId);
    }
}