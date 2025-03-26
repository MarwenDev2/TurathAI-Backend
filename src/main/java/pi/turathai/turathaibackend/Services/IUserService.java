package pi.turathai.turathaibackend.Services;

import pi.turathai.turathaibackend.Entites.*;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User createUser(User user);
    Optional<User> getUserById(Long userId);
    List<User> getAllUsers();
    User updateUser(Long userId, User userDetails);
    void deleteUser(Long userId);
    User findUserByEmail(String email);
    boolean existsByEmail(String email);
}