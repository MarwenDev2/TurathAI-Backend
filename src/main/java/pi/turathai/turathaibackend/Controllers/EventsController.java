package pi.turathai.turathaibackend.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.DTO.EventDto;
import pi.turathai.turathaibackend.Entites.Event;
import pi.turathai.turathaibackend.Entites.HeritageSite;
import pi.turathai.turathaibackend.Entites.Image;
import pi.turathai.turathaibackend.Repositories.EventRepository;
import pi.turathai.turathaibackend.Repositories.HeritageSiteRepo;
import pi.turathai.turathaibackend.Repositories.ImageRepo;
import pi.turathai.turathaibackend.Services.IEventsService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins= "*")
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventsController {

    private final IEventsService eventService;
    private final HeritageSiteRepo heritageSiteRepository;
    private final EventRepository eventRepository;
    private final ImageRepo imageRepository;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }
    @GetMapping("/{EventId}")
    public Event getEventById(@PathVariable Long EventId) {
        return eventService.getEventById(EventId);
    }
    // EventController.java
    @PostMapping
    public Event createEvent(@RequestBody EventDto eventDto) {
        Event event = new Event();
        event.setName(eventDto.getName());
        event.setDescription(eventDto.getDescription());
        event.setStartDate(eventDto.getStartDate());
        event.setEndDate(eventDto.getEndDate());
        event.setLocation(eventDto.getLocation());

        // Set heritage site
        HeritageSite site = heritageSiteRepository.findById(eventDto.getHeritageSiteId())
                .orElseThrow(() -> new RuntimeException("Heritage site not found"));
        event.setHeritageSite(site);

        // Save event first to get ID
        Event savedEvent = eventRepository.save(event);

        // Handle images
        if (eventDto.getImageIds() != null && !eventDto.getImageIds().isEmpty()) {
            Set<Image> images = new HashSet<>(imageRepository.findAllById(eventDto.getImageIds()));
            savedEvent.setImages(images);
            return eventRepository.save(savedEvent);
        }

        return savedEvent;
    }

    @PutMapping("/{EventId}")
    public Event updateEvent(@PathVariable Long EventId, @RequestBody Event event) {
        return eventService.updateEvent(EventId, event);
    }
    @DeleteMapping("/{EventId}")
    public void deleteEvent(@PathVariable Long EventId) {
        eventService.deleteEvent(EventId);
    }


    @GetMapping("/count")
    public long getEventCount() {
        return eventService.countAllEvents();
    }

    @GetMapping("/upcoming-count")
    public long getUpcomingEventCount() {
        return eventService.countUpcomingEvents();
    }
}
