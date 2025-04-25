package pi.turathai.turathaibackend.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Entites.Badge;
import pi.turathai.turathaibackend.Services.BadgeService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins= "http://Localhost:4200")
@RestController
@RequestMapping("/api/badges")
public class BadgeController {
    private final BadgeService badgeService;

    public BadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @PostMapping("/add/")
    public ResponseEntity<Badge> createBadge(@RequestBody Badge badge) {
        return ResponseEntity.ok(badgeService.createBadge(badge));
    }

    @GetMapping("/all/")
    public ResponseEntity<List<Badge>> getAllBadges() {
        return ResponseEntity.ok(badgeService.getAllBadges());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<Badge>> getBadgeById(@PathVariable Long id) {
        return ResponseEntity.ok(badgeService.getBadgeById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Badge> updateBadge(@PathVariable Long id, @RequestBody Badge newBadge) {
        return ResponseEntity.ok(badgeService.updateBadge(id, newBadge));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBadge(@PathVariable Long id) {
        badgeService.deleteBadge(id);
        return ResponseEntity.ok("Badge deleted successfully!");
    }
}
