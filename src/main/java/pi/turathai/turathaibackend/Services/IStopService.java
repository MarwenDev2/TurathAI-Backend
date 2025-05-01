package pi.turathai.turathaibackend.Services;

import pi.turathai.turathaibackend.Entites.Stop;
import java.util.List;

public interface IStopService {
    List<Stop> getByItineraryId(long itineraryId);
    Stop add(Stop stop);
    Stop update(Stop stop);
    void remove(long id);
    Stop getById(long id);
    List<Stop> getAll();
    List<Stop> reorderStops(List<Stop> stops);
    void deleteByItineraryId(Long itineraryId);

    List<Stop> getBySiteId(Long siteId);

}