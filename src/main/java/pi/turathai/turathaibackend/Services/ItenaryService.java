package pi.turathai.turathaibackend.Services;
import pi.turathai.turathaibackend.Controllers.EventsController;
import pi.turathai.turathaibackend.DTO.ItineraryStatisticsDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.Itinery;
import pi.turathai.turathaibackend.Repositories.HeritageSiteRepo;
import pi.turathai.turathaibackend.Repositories.ItenaryRepo;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ItenaryService implements IItineryService {

    @Autowired
    private  ItenaryRepo itenaryRepository;

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
}