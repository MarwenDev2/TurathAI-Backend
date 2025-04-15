package pi.turathai.turathaibackend.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.CrowdHeatMap;
import pi.turathai.turathaibackend.Repositories.CrowdHeatMapRepo;

import java.util.List;

@Service
@Slf4j
public class CrowdHeatMapService implements ICrowdHeatMapService {

    @Autowired
    private CrowdHeatMapRepo crowdHeatMapRepository;

    @Override
    public CrowdHeatMap add(CrowdHeatMap crowdHeatMap) {
        log.info("Adding CrowdHeatMap: {}", crowdHeatMap);
        return crowdHeatMapRepository.save(crowdHeatMap);
    }

    @Override
    public CrowdHeatMap update(CrowdHeatMap crowdHeatMap) {
        log.info("Updating CrowdHeatMap: {}", crowdHeatMap);
        return crowdHeatMapRepository.save(crowdHeatMap);
    }

    @Override
    public void remove(long id) {
        log.info("Removing CrowdHeatMap with ID: {}", id);
        crowdHeatMapRepository.deleteById(id);
    }

    @Override
    public CrowdHeatMap getById(long id) {
        log.info("Fetching CrowdHeatMap with ID: {}", id);
        return crowdHeatMapRepository.findById(id).orElse(null);
    }

    @Override
    public List<CrowdHeatMap> getAll() {
        log.info("Fetching all CrowdHeatMaps");
        return crowdHeatMapRepository.findAll();
    }
}