package pi.turathai.turathaibackend.Controllers;



import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Entites.LocalInsight;
import pi.turathai.turathaibackend.Repositories.LocalInsightRepository;
import pi.turathai.turathaibackend.Security.EmailService;
import pi.turathai.turathaibackend.Services.LocalInsightService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/local-insights")
@RequiredArgsConstructor
public class LocalInsightController {

    private final LocalInsightService localInsightService;
    private final LocalInsightRepository localInsightRepository;
    private final EmailService emailService;

    @GetMapping
    public List<LocalInsight> getAllLocalInsights() {
        return localInsightService.getAllLocalInsights();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalInsight> getLocalInsightById(@PathVariable Long id) {
        return localInsightService.getLocalInsightById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/creation")
    public ResponseEntity<?> createLocalInsight(@RequestBody LocalInsight localInsight) {
        try {
            LocalInsight savedInsight = localInsightService.saveLocalInsight(localInsight);
            localInsightService.sendConfirmationEmail(savedInsight);
            return ResponseEntity.ok(savedInsight);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating local insight: " + e.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateLocalInsight(@PathVariable Long id, @RequestBody LocalInsight updatedInsight) {
        if (!localInsightService.getLocalInsightById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        try {
            updatedInsight.setId(id);
            LocalInsight savedInsight = localInsightService.saveLocalInsight(updatedInsight);
            return ResponseEntity.ok(savedInsight);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating local insight: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocalInsight(@PathVariable Long id) {
        if (localInsightService.getLocalInsightById(id).isPresent()) {
            localInsightService.deleteLocalInsight(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<?> likeLocalInsight(@PathVariable Long id) {
        LocalInsight insight = localInsightService.getLocalInsightById(id).orElse(null);
        if (insight == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            // Initialiser likes à 0 si null
            if (insight.getLikes() == null) {
                insight.setLikes(0);
            }
            insight.setLikes(insight.getLikes() + 1);
            LocalInsight updatedInsight = localInsightService.saveLocalInsight(insight);
            return ResponseEntity.ok(updatedInsight);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding like: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/dislike")
    public ResponseEntity<?> dislikeLocalInsight(@PathVariable Long id) {
        LocalInsight insight = localInsightService.getLocalInsightById(id).orElse(null);
        if (insight == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            // Initialiser dislikes à 0 si null
            if (insight.getDislikes() == null) {
                insight.setDislikes(0);
            }
            insight.setDislikes(insight.getDislikes() + 1);
            LocalInsight updatedInsight = localInsightService.saveLocalInsight(insight);
            return ResponseEntity.ok(updatedInsight);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding dislike: " + e.getMessage());
        }
    }



    @DeleteMapping("/{id}/like")
    public ResponseEntity<?> removeLike(@PathVariable Long id) {
        LocalInsight insight = localInsightService.getLocalInsightById(id).orElse(null);
        if (insight == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            // Initialiser likes à 0 si null
            if (insight.getLikes() == null) {
                insight.setLikes(0);
            }
            if (insight.getLikes() > 0) {
                insight.setLikes(insight.getLikes() - 1);
                LocalInsight updatedInsight = localInsightService.saveLocalInsight(insight);
                return ResponseEntity.ok(updatedInsight);
            }
            return ResponseEntity.ok(insight);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error removing like: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}/dislike")
    public ResponseEntity<?> removeDislike(@PathVariable Long id) {
        LocalInsight insight = localInsightService.getLocalInsightById(id).orElse(null);
        if (insight == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            // Initialiser dislikes à 0 si null
            if (insight.getDislikes() == null) {
                insight.setDislikes(0);
            }
            if (insight.getDislikes() > 0) {
                insight.setDislikes(insight.getDislikes() - 1);
                LocalInsight updatedInsight = localInsightService.saveLocalInsight(insight);
                return ResponseEntity.ok(updatedInsight);
            }
            return ResponseEntity.ok(insight);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error removing dislike: " + e.getMessage());
        }
    }

    @GetMapping("/insights-by-type")
    public ResponseEntity<List<Map<String, Object>>> getInsightsByType() {
        List<Map<String, Object>> insightsByType = localInsightService.getInsightsByType();
        return ResponseEntity.ok(insightsByType);
    }

    @PostMapping("/notify")
    public ResponseEntity<?> sendNotification(@RequestBody Map<String, Object> notification) {
        try {
            Long localInsightId = Long.valueOf(notification.get("localInsightId").toString());
            String userEmail = (String) notification.get("userEmail");
            String userName = (String) notification.get("userName");

            // Fix: safely retrieve the LocalInsight
            Optional<LocalInsight> optionalInsight = localInsightService.getLocalInsightById(localInsightId);
            if (optionalInsight.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Local Insight not found", "status", "ERROR"));
            }

            LocalInsight localInsight = optionalInsight.get(); // Now it's safe to use .get()
            String insightTitle = localInsight.getTitle();

            // Send the email notification
            emailService.sendLocalInsightNotificationEmail("marwenfeki214@gmail.com", userName, insightTitle);

            return ResponseEntity.ok().body(Map.of(
                    "message", "Notification email sent successfully",
                    "status", "SUCCESS"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", e.getMessage(),
                    "status", "ERROR"
            ));
        }
    }

    @GetMapping("/site/{siteId}")
    public ResponseEntity<List<LocalInsight>> getLocalInsightsBySiteId(@PathVariable Long siteId) {
        List<LocalInsight> insights = localInsightService.getLocalInsightsBySiteId(siteId);
        return ResponseEntity.ok(insights);
    }

}
