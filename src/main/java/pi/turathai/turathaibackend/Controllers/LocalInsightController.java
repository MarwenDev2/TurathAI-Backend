package pi.turathai.turathaibackend.Controllers;



import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Entites.LocalInsight;
import pi.turathai.turathaibackend.Repositories.LocalInsightRepository;
import pi.turathai.turathaibackend.Services.LocalInsightService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@CrossOrigin(origins= "http://Localhost:4200")
@RestController
@RequestMapping("/api/local-insights")
@RequiredArgsConstructor
public class LocalInsightController {

    public LocalInsightService localInsightService;
    private LocalInsightRepository localInsightRepository;

    @Autowired
    public LocalInsightController(LocalInsightService localInsightService, LocalInsightRepository localInsightRepository) {
        this.localInsightService = localInsightService;
        this.localInsightRepository = localInsightRepository;
    }


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

    @PostMapping("creation")
    public ResponseEntity<?> createLocalInsight(@RequestBody LocalInsight localInsight) {
        try {
            LocalInsight savedInsight = localInsightService.saveLocalInsight(localInsight);

            // Envoi d'email après création réussie
            localInsightService.sendConfirmationEmail(savedInsight);

            return ResponseEntity.ok(savedInsight);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la création: " + e.getMessage());
        }
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<LocalInsight> updateLocalInsight(@PathVariable Long id, @RequestBody LocalInsight updatedInsight) {
        return localInsightService.getLocalInsightById(id)
                .map(existingInsight -> {
                    updatedInsight.setId(id);
                    return ResponseEntity.ok(localInsightService.saveLocalInsight(updatedInsight));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocalInsight(@PathVariable Long id) {
        if (localInsightService.getLocalInsightById(id).isPresent()) {
            localInsightService.deleteLocalInsight(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }






}
