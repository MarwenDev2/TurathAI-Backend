package pi.turathai.turathaibackend.Controllers;



import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Entites.LocalInsight;
import pi.turathai.turathaibackend.Services.LocalInsightService;

import java.util.List;

@CrossOrigin(origins= "http://Localhost:4200")
@RestController
@RequestMapping("/api/local-insights")
@RequiredArgsConstructor
public class LocalInsightController {

    public LocalInsightService localInsightService;
    @Autowired
    public LocalInsightController(LocalInsightService localInsightService) {
        this.localInsightService = localInsightService;
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
    public LocalInsight createLocalInsight(@RequestBody LocalInsight localInsight) {
        return localInsightService.saveLocalInsight(localInsight);
    }

    @PutMapping("/{id}")
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
