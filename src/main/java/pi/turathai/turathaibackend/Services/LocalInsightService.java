package pi.turathai.turathaibackend.Services;

import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.LocalInsight;
import pi.turathai.turathaibackend.Repositories.LocalInsightRepository;

import java.util.List;
import java.util.Optional;
@Service

public class LocalInsightService implements ILocalInsight {
    public LocalInsightRepository localInsightRepository;

    public LocalInsightService(LocalInsightRepository localInsightRepository) {
        this.localInsightRepository = localInsightRepository;
    }

    @Override
    public List<LocalInsight> getAllLocalInsights() {
        return localInsightRepository.findAll();
    }

    @Override
    public Optional<LocalInsight> getLocalInsightById(Long id) {
        return localInsightRepository.findById(id);
    }

    @Override
    public LocalInsight saveLocalInsight(LocalInsight localInsight) {
        return localInsightRepository.save(localInsight);
    }

    @Override
    public void deleteLocalInsight(Long id) {
        localInsightRepository.deleteById(id);
    }
}
