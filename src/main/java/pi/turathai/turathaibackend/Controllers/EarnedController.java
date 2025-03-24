/*
package pi.turathai.turathaibackend.Controllers;


import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Entites.Earned;
import pi.turathai.turathaibackend.Services.EarnedService;

import java.util.List;

@RestController
@RequestMapping("/earned")
public class EarnedController {
    private final EarnedService earnedService;

    public EarnedController(EarnedService earnedService) {
        this.earnedService = earnedService;
    }

    @PostMapping("/award/{userId}/{badgeId}")
    public Earned awardBadge(@PathVariable Long userId, @PathVariable Long badgeId) {
        return earnedService.awardBadgeToUser(userId, badgeId);
    }

    @GetMapping("/user/{userId}")
    public List<Earned> getUserBadges(@PathVariable Long userId) {
        return earnedService.getBadgesByUser(userId);
    }

    @GetMapping("/badge/{badgeId}")
    public List<Earned> getBadgeUsers(@PathVariable Long badgeId) {
        return earnedService.getUsersByBadge(badgeId);
    }

    @DeleteMapping("/{earnedId}")
    public void revokeBadge(@PathVariable Long earnedId) {
        earnedService.revokeBadge(earnedId);
    }
}
*/