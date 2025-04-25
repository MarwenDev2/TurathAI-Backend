package pi.turathai.turathaibackend.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.DTO.ItineraryStatisticsDTO;
import pi.turathai.turathaibackend.Entites.Itinery;
import pi.turathai.turathaibackend.Services.IItineryService;

import java.util.List;

@CrossOrigin(origins= "http://localhost:4200")
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
}