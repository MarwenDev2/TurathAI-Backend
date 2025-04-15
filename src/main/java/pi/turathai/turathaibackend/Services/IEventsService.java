package pi.turathai.turathaibackend.Services;
import pi.turathai.turathaibackend.Entites.*;

import java.util.List;
import java.util.Optional;
public interface IEventsService {
    List<Event> getAllEvents();
    Event getEventById(Long id);
    Event createEvent(Event event);
    Event updateEvent(Long id, Event event);
    void deleteEvent(Long id);
}
