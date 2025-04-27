package pi.turathai.turathaibackend.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Entites.Event;
import pi.turathai.turathaibackend.Entites.Itinery;
import pi.turathai.turathaibackend.Services.IEventsService;

import java.util.List;

@CrossOrigin(origins= "http://Localhost:4200")
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventsController {

    private final IEventsService eventService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{EventId}")
    public Event getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @PutMapping("/{EventId}")
    public Event updateEvent(@PathVariable Long id, @RequestBody Event event) {
        return eventService.updateEvent(id, event);
    }

    @DeleteMapping("/{EventId}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }


}
