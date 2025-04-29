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

    List<Itinery> getItinerariesByUserId(Long userId);  // Add this method
    Itinery assignItineraryToUser(Long itineraryId, Long userId);
    void removeItineraryFromUser(Long itineraryId);

    List<Itinery> getItinerariesBySiteId(Long siteId);

}