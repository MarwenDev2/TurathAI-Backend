package pi.turathai.turathaibackend.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.DTO.ItineraryStatisticsDTO;
import pi.turathai.turathaibackend.Entites.Itinery;
import pi.turathai.turathaibackend.Services.IItineryService;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins= "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/itineries")
public class ItenaryController {

    @Autowired
    private IItineryService itineryService;

    @PostMapping("/add")
    public Itinery addItinery(@RequestBody Itinery itinery) {
        return itineryService.add(itinery);
    }

    @PutMapping("/update")
    public Itinery updateItinery(@RequestBody Itinery itinery) {
        return itineryService.update(itinery);
    }

    @DeleteMapping("/remove/{id}")
    public void removeItinery(@PathVariable long id) {
        itineryService.remove(id);
    }

    @GetMapping("/get/{id}")
    public Itinery getItineryById(@PathVariable long id) {
        return itineryService.getById(id);
    }

    @GetMapping("/all")
    public List<Itinery> getAllItineries() {
        return itineryService.getAll();
    }

    @GetMapping("/statistics")
    public ResponseEntity<ItineraryStatisticsDTO> getStatistics() {
        return ResponseEntity.ok(itineryService.getStatistics()); // Fixed: using instance variable
    }

    @GetMapping("/site/{siteId}")
    public List<Itinery> getItinerariesBySiteId(@PathVariable Long siteId) {
        return itineryService.getItinerariesBySiteId(siteId);
    }

    @GetMapping("/user/{userId}")
    public List<Itinery> getItinerariesByUserId(@PathVariable Long userId) {
        return itineryService.getItinerariesByUserId(userId);
    }

    @PostMapping("/assign")
    public Itinery assignItineraryToUser(@RequestBody Map<String, Long> payload) {
        Long itineraryId = payload.get("itineraryId");
        Long userId = payload.get("userId");
        return itineryService.assignItineraryToUser(itineraryId, userId);
    }

    @DeleteMapping("/unassign/{itineraryId}")
    public ResponseEntity<Void> removeItineraryFromUser(@PathVariable Long itineraryId) {
        itineryService.removeItineraryFromUser(itineraryId);
        return ResponseEntity.ok().build();
    }

}