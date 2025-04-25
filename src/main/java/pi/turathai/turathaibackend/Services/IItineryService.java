package pi.turathai.turathaibackend.Services;

import pi.turathai.turathaibackend.DTO.ItineraryStatisticsDTO;
import pi.turathai.turathaibackend.Entites.Itinery;

import java.util.List;

public interface IItineryService {
    // Add this method to your IItineraryService interface
    ItineraryStatisticsDTO getStatistics();
    Itinery add(Itinery itinery);
    Itinery update(Itinery itinery);
    void remove(long id);
    Itinery getById(long id);
    List<Itinery> getAll();
}