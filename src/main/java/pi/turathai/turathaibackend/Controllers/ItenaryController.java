package pi.turathai.turathaibackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Entites.Itinery;
import pi.turathai.turathaibackend.services.IItineryService;

import java.util.List;

@RestController
@RequestMapping("/itineries")
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
}