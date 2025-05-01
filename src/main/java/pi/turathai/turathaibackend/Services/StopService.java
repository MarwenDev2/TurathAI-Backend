package pi.turathai.turathaibackend.Services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.Stop;
import pi.turathai.turathaibackend.Repositories.StopRepo;

import java.util.List;

@Service
@Slf4j
public class StopService implements IStopService {

    @Autowired
    private StopRepo stopRepository;

    @Override
    public Stop add(Stop stop) {
        log.info("Adding stop: {}", stop);
        return stopRepository.save(stop);
    }

    @Override
    public Stop update(Stop stop) {
        log.info("Updating stop: {}", stop);
        return stopRepository.save(stop);
    }

    @Override
    public void remove(long id) {
        log.info("Removing stop with ID: {}", id);
        stopRepository.deleteById(id);
    }

    @Override
    public Stop getById(long id) {
        log.info("Fetching stop with ID: {}", id);
        return stopRepository.findById(id).orElse(null);
    }

    @Override
    public List<Stop> getAll() {
        log.info("Fetching all stops");
        return stopRepository.findAll();
    }
    @Override
    public List<Stop> getByItineraryId(long itineraryId) {
        log.info("Fetching stops for itinerary with ID: {}", itineraryId);
        return stopRepository.findByItineryId(itineraryId);
    }
    @Transactional
    public List<Stop> reorderStops(List<Stop> stops) {
        // Validate all stops belong to the same itinerary
        Long itineraryId = stops.isEmpty() ? null : stops.get(0).getItinery().getId();

        for (Stop stop : stops) {
            if (stop.getItinery() == null || !stop.getItinery().getId().equals(itineraryId)) {
                throw new IllegalArgumentException("All stops must belong to the same itinerary");
            }
        }

        // Save all stops with their new order
        return stopRepository.saveAll(stops);
    }

    public void deleteByItineraryId(Long itineraryId) {
        stopRepository.deleteByItineryId(itineraryId);
    }

    @Override
    public List<Stop> getBySiteId(Long siteId) {
        log.info("Fetching stops for heritage site with ID: {}", siteId);
        return stopRepository.findByHeritageSiteId(siteId);
    }

}