package pi.turathai.turathaibackend.Controllers;

import pi.turathai.turathaibackend.DTO.UserPreferencesDTO;
import pi.turathai.turathaibackend.Entites.User;
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
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public UserPreferences createUserPreferences(@RequestBody UserPreferencesDTO preferencesDTO) {
        if (preferencesDTO.getUserId() == null) {
            throw new IllegalArgumentException("User ID must be provided");
        }

        User user = userRepository.findById(preferencesDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + preferencesDTO.getUserId()));

        UserPreferences preferences = new UserPreferences();
        preferences.setPreferenceCategories(preferencesDTO.getPreferenceCategories());
        preferences.setTravelStyles(preferencesDTO.getTravelStyles());
        preferences.setBudgetRange(preferencesDTO.getBudgetRange());
        preferences.setLanguagePreferences(preferencesDTO.getLanguagePreferences());
        preferences.setUser(user);

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