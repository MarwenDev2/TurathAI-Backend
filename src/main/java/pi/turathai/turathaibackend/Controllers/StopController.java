package pi.turathai.turathaibackend.Controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Entites.Stop;
import pi.turathai.turathaibackend.Services.IStopService;

import java.util.List;

@CrossOrigin(origins= "*")
@RestController
@RequestMapping("/api/stops")
public class StopController {

    @Autowired
    private IStopService stopService;

    @PostMapping("/add")
    public Stop addStop(@RequestBody Stop stop) {
        return stopService.add(stop);
    }

    @PutMapping("/update")
    public Stop updateStop(@RequestBody Stop stop) {
        return stopService.update(stop);
    }

    @DeleteMapping("/remove/{id}")
    public void removeStop(@PathVariable long id) {
        stopService.remove(id);
    }

    @GetMapping("/get/{id}")
    public Stop getStopById(@PathVariable long id) {
        return stopService.getById(id);
    }

    @GetMapping("/itinerary/{itineraryId}")
    public List<Stop> getStopsByItineraryId(@PathVariable long itineraryId) {
        return stopService.getByItineraryId(itineraryId);
    }

    @PutMapping("/reorder")
    @Transactional
    public List<Stop> reorderStops(@RequestBody List<Stop> stops) {
        return stopService.reorderStops(stops);
    }

    @GetMapping("/all")
    public List<Stop> getAllStops() {
        return stopService.getAll();
    }

    @DeleteMapping("/itinerary/{itineraryId}")
    public ResponseEntity<Void> deleteByItineraryId(@PathVariable Long itineraryId) {
        stopService.deleteByItineraryId(itineraryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/site/{siteId}")
    public List<Stop> getStopsBySiteId(@PathVariable Long siteId) {
        return stopService.getBySiteId(siteId);
    }

}