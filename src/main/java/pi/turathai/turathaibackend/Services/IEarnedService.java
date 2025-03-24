package pi.turathai.turathaibackend.Services;

import pi.turathai.turathaibackend.Entites.Earned;

import java.util.List;

public interface IEarnedService {
    Earned awardBadgeToUser(Long userId, Long badgeId);
    List<Earned> getBadgesByUser(Long userId);
    List<Earned> getUsersByBadge(Long badgeId);
    void revokeBadge(Long earnedId);
}
