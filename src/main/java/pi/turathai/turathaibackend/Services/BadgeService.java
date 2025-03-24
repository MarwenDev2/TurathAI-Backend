package pi.turathai.turathaibackend.Services;

import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.Badge;
import pi.turathai.turathaibackend.Repositories.BadgeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BadgeService implements IBadgeService {
    private final BadgeRepository badgeRepository;

    public BadgeService(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    public Badge createBadge(Badge badge) {
        if (badgeRepository.existsByName(badge.getName())) {
            throw new RuntimeException("Badge with this name already exists!");
        }
        return badgeRepository.save(badge);
    }

    public List<Badge> getAllBadges() {
        return badgeRepository.findAll();
    }

    public Optional<Badge> getBadgeById(Long id) {
        return badgeRepository.findById(id);
    }

    public Badge updateBadge(Long id, Badge newBadge) {
        return badgeRepository.findById(id)
                .map(badge -> {
                    badge.setName(newBadge.getName());
                    badge.setDescription(newBadge.getDescription());
                    return badgeRepository.save(badge);
                })
                .orElseThrow(() -> new RuntimeException("Badge not found!"));
    }

    public void deleteBadge(Long id) {
        if (!badgeRepository.existsById(id)) {
            throw new RuntimeException("Badge not found!");
        }
        badgeRepository.deleteById(id);
    }
}

/*
✔️ Create Badge (Prevents duplicates)
✔️ Get all badges
✔️ Get badge by ID
✔️ Update badge
✔️ Delete badge (Ensures badge exists before deletion)
* */
