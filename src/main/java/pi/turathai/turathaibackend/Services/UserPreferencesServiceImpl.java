package pi.turathai.turathaibackend.Services;

import pi.turathai.turathaibackend.Entites.UserPreferences;
import pi.turathai.turathaibackend.Repositories.UserPreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPreferencesServiceImpl implements IUserPreferencesService {

    @Autowired
    private UserPreferencesRepository userPreferencesRepository;

    @Override
    public UserPreferences createUserPreferences(UserPreferences preferences) {
        return userPreferencesRepository.save(preferences);
    }

    @Override
    public Optional<UserPreferences> getUserPreferencesById(Long preferenceId) {
        return userPreferencesRepository.findById(preferenceId);
    }

    @Override
    public void deleteUserPreferences(Long preferenceId) {
        userPreferencesRepository.deleteById(preferenceId);
    }

    @Override
    public UserPreferences findByUserId(Long userId) {
        return userPreferencesRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("UserPreferences not found for userId: " + userId));
    }
}