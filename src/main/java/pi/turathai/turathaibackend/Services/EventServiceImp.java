package pi.turathai.turathaibackend.Services;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.Event;
import pi.turathai.turathaibackend.Repositories.EventRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImp implements IEventsService{
    private final EventRepository eventRepository;

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Long id, Event updatedEvent) {
        Event existing = getEventById(id);
        existing.setName(updatedEvent.getName());
        existing.setDescription(updatedEvent.getDescription());
        existing.setStartDate(updatedEvent.getStartDate());
        existing.setEndDate(updatedEvent.getEndDate());
        existing.setLocation(updatedEvent.getLocation());
        existing.setHeritageSite(updatedEvent.getHeritageSite());
        existing.setImages(updatedEvent.getImages());
        return eventRepository.save(existing);
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public long countAllEvents() {
        return eventRepository.count();
    }

    @Override
    public long countUpcomingEvents() {
        Date currentDate = new Date();
        return eventRepository.countByStartDateAfter(currentDate);
    }
}
