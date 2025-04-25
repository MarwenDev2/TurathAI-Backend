package pi.turathai.turathaibackend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.PasswordResetToken;
import pi.turathai.turathaibackend.Entites.User;
import pi.turathai.turathaibackend.Repositories.*;

import java.util.Date;

@Service
public class PasswordResetTokenService {

    private static final int EXPIRATION = 24 * 60; // 24 hours in minutes

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user, EXPIRATION);
        tokenRepository.save(myToken);
    }

    public String validatePasswordResetToken(String token) {
        PasswordResetToken passToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        System.out.println("Received token: " + token);

        if (isTokenExpired(passToken)) {
            return "Token has expired";
        }
        return null;
    }

    public User getUserByPasswordResetToken(String token) {
        return tokenRepository.findByToken(token)
                .map(PasswordResetToken::getUser)
                .orElse(null);
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        System.out.println("Token expiry date: " + passToken.getExpiryDate());
        System.out.println("Current date: " + new Date());

        return passToken.getExpiryDate().before(new Date());
    }
}