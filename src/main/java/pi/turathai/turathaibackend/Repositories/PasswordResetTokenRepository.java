package pi.turathai.turathaibackend.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pi.turathai.turathaibackend.Entites.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
}