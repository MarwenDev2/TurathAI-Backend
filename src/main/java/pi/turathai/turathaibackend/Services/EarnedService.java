/*
package pi.turathai.turathaibackend.Services;


import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.*;
import pi.turathai.turathaibackend.Repositories.BadgeRepository;
import pi.turathai.turathaibackend.Repositories.EarnedRepository;
import pi.turathai.turathaibackend.Repositories.UserRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class EarnedService implements IEarnedService {
    private final EarnedRepository earnedRepository;
    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;

    public EarnedService(EarnedRepository earnedRepository, UserRepository userRepository, BadgeRepository badgeRepository) {
        this.earnedRepository = earnedRepository;
        this.userRepository = userRepository;
        this.badgeRepository = badgeRepository;
    }

    @Override
    public Earned awardBadgeToUser(Long userId, Long badgeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Badge badge = badgeRepository.findById(badgeId)
                .orElseThrow(() -> new RuntimeException("Badge not found!"));

        if (earnedRepository.existsByUserIdAndBadgeId(userId, badgeId)) {
            throw new RuntimeException("User already earned this badge!");
        }

        Earned earned = new Earned();
        earned.setUser(user);
        earned.setBadge(badge);
        earned.setEarnedAt(Date.valueOf(LocalDate.now()));

        return earnedRepository.save(earned);
    }

    @Override
    public List<Earned> getBadgesByUser(Long userId) {
        return earnedRepository.findByUserId(userId);
    }

    @Override
    public List<Earned> getUsersByBadge(Long badgeId) {
        return earnedRepository.findByBadgeId(badgeId);
    }

    @Override
    public void revokeBadge(Long earnedId) {
        if (!earnedRepository.existsById(earnedId)) {
            throw new RuntimeException("Earned record not found!");
        }
        earnedRepository.deleteById(earnedId);
    }
}
*/

