package pi.turathai.turathaibackend.Controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final String AI_SERVICE_URL = "http://localhost:5000/connections";

    @GetMapping("/connections/{siteId}")
    public ResponseEntity<?> getConnections(@PathVariable int siteId) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Object connections = restTemplate.getForObject(
                    AI_SERVICE_URL + "/" + siteId,
                    Object.class // Use Object.class to handle any JSON structure
            );
            return ResponseEntity.ok(connections);
        } catch (Exception e) {
            return ResponseEntity.status(503)
                    .body(Map.of(
                            "error", "AI service unavailable",
                            "details", e.getMessage()
                    ));
        }
    }
}