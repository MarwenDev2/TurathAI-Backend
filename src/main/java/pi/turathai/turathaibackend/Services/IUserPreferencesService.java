package pi.turathai.turathaibackend.Services;

import pi.turathai.turathaibackend.Entites.UserPreferences;

import java.util.Optional;

public interface IUserPreferencesService {
    UserPreferences createUserPreferences(UserPreferences preferences);
    Optional<UserPreferences> getUserPreferencesById(Long preferenceId);
    void deleteUserPreferences(Long preferenceId);
    UserPreferences findByUserId(Long userId);
}
