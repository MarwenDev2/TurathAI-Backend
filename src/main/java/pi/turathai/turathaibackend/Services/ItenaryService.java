package pi.turathai.turathaibackend.Services;
import pi.turathai.turathaibackend.DTO.ItineraryStatisticsDTO;
import pi.turathai.turathaibackend.Entites.Stop;
import pi.turathai.turathaibackend.Entites.User;
import pi.turathai.turathaibackend.Repositories.ItenaryRepo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.Itinery;
import pi.turathai.turathaibackend.Repositories.StopRepo;
import pi.turathai.turathaibackend.Repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItenaryService implements IItineryService {

    @Autowired
    private ItenaryRepo itenaryRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StopRepo stopRepo;


    @Override
    public Itinery add(Itinery itinery) {
        log.info("Adding itinery: {}", itinery);
        return itenaryRepository.save(itinery);
    }

    @Override
    public Itinery update(Itinery itinery) {
        log.info("Updating itinery: {}", itinery);
        return itenaryRepository.save(itinery);
    }

    public Optional<Itinery> findById(Long id) {
        return itenaryRepository.findById(id);
    }

    @Override
    public void remove(long id) {
        log.info("Removing itinery with ID: {}", id);
        itenaryRepository.deleteById(id);
    }

    @Override
    public Itinery getById(long id) {
        log.info("Fetching itinery with ID: {}", id);
        return itenaryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Itinery> getAll() {
        log.info("Fetching all itineries");
        return itenaryRepository.findAll();
    }


    @Override
    public ItineraryStatisticsDTO getStatistics() {
        log.info("Fetching itinerary statistics");

        List<Itinery> allItineraries = itenaryRepository.findAll(); // Fixed: using instance variable

        // Calculate the statistics
        long totalCount = allItineraries.size();
        long lowBudgetCount = allItineraries.stream()
                .filter(itinerary -> itinerary.getBudget() < 4000)
                .count();

        double totalBudget = allItineraries.stream()
                .mapToDouble(Itinery::getBudget)
                .sum();

        double averageBudget = totalCount > 0 ? totalBudget / totalCount : 0;

        double minBudget = allItineraries.stream()
                .mapToDouble(Itinery::getBudget)
                .min()
                .orElse(0);

        double maxBudget = allItineraries.stream()
                .mapToDouble(Itinery::getBudget)
                .max()
                .orElse(0);

        return new ItineraryStatisticsDTO(
                totalCount,
                lowBudgetCount,
                totalBudget,
                averageBudget,
                minBudget,
                maxBudget
        );
    }

    @Override
    public List<Itinery> getItinerariesByUserId(Long userId) {
        log.info("Fetching itineraries for user with ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return itenaryRepository.findByUser(user);
    }

    @Override
    public Itinery assignItineraryToUser(Long itineraryId, Long userId) {
        log.info("Assigning itinerary {} to user {}", itineraryId, userId);
        Itinery itinery = itenaryRepository.findById(itineraryId)
                .orElseThrow(() -> new RuntimeException("Itinerary not found with id: " + itineraryId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        itinery.setUser(user);
        return itenaryRepository.save(itinery);
    }

    @Override
    public void removeItineraryFromUser(Long itineraryId) {
        log.info("Removing user association from itinerary with ID: {}", itineraryId);
        Itinery itinery = itenaryRepository.findById(itineraryId)
                .orElseThrow(() -> new RuntimeException("Itinerary not found with id: " + itineraryId));

        itinery.setUser(null);
        itenaryRepository.save(itinery);
    }

    @Override
    public List<Itinery> getItinerariesBySiteId(Long siteId) {
        log.info("Fetching itineraries for site with ID: {}", siteId);
        List<Stop> stops = stopRepo.findByHeritageSiteId(siteId);

        return stops.stream()
                .map(Stop::getItinery)
                .distinct()
                .collect(Collectors.toList());
    }
}