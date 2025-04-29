package pi.turathai.turathaibackend.Services;

import pi.turathai.turathaibackend.DTO.*;
import pi.turathai.turathaibackend.Entites.User;
import pi.turathai.turathaibackend.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserStatisticsService {

    private final UserRepository userRepository;

    public UserStatisticsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserGrowthDTO> getUserGrowthStatistics() {
        return userRepository.getUserGrowthStatistics().stream()
                .map(result -> new UserGrowthDTO(
                        (String) result[0], // month
                        (Long) result[1]    // count
                ))
                .collect(Collectors.toList());
    }

    public List<UserCountryDTO> getUserCountryStatistics() {
        return userRepository.getUserCountryStatistics().stream()
                .map(result -> new UserCountryDTO(
                        (String) result[0], // country
                        (Long) result[1]    // count
                ))
                .collect(Collectors.toList());
    }

    public List<UserRoleDTO> getUserRoleStatistics() {
        return userRepository.getUserRoleStatistics().stream()
                .map(result -> new UserRoleDTO(
                        (String) result[0], // role
                        (Long) result[1]    // count
                ))
                .collect(Collectors.toList());
    }

    public List<User> getRecentUsers(int limit) {
        return userRepository.findTopNByOrderByCreatedAtDesc(limit);
    }
}