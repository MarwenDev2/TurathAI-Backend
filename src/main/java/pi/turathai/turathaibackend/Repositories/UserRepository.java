package pi.turathai.turathaibackend.Repositories;

import pi.turathai.turathaibackend.Entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Existing methods
    User findByEmail(String email);
    boolean existsByEmail(String email);
    User findTopByOrderByIdDesc();

    @Query(value = "SELECT DATE_FORMAT(created_at, '%Y-%m') AS month, COUNT(*) AS count " +
            "FROM user " +  // Changed from 'users' to 'user'
            "GROUP BY DATE_FORMAT(created_at, '%Y-%m') " +
            "ORDER BY month", nativeQuery = true)
    List<Object[]> getUserGrowthStatistics();

    @Query(value = "SELECT COALESCE(origin_country, 'Unknown') AS country, COUNT(*) AS count " +
            "FROM user " +  // Changed from 'users' to 'user'
            "GROUP BY COALESCE(origin_country, 'Unknown')", nativeQuery = true)
    List<Object[]> getUserCountryStatistics();

    @Query(value = "SELECT role, COUNT(*) AS count " +
            "FROM user " +  // Changed from 'users' to 'user'
            "GROUP BY role", nativeQuery = true)
    List<Object[]> getUserRoleStatistics();

    @Query(value = "SELECT * FROM user ORDER BY created_at DESC LIMIT ?1", nativeQuery = true)
    List<User> findTopNByOrderByCreatedAtDesc(int limit);

    @Query("SELECT u.email FROM User u")
    List<String> findAllEmails();
}