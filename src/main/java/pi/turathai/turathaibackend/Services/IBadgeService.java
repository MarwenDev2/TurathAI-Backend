package pi.turathai.turathaibackend.Services;

import pi.turathai.turathaibackend.Entites.Badge;

import java.util.List;
import java.util.Optional;

public interface IBadgeService {
    Badge createBadge(Badge badge);
    List<Badge> getAllBadges();
    Optional<Badge> getBadgeById(Long id);
    Badge updateBadge(Long id, Badge newBadge);
    void deleteBadge(Long id);
}
