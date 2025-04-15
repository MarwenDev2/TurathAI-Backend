package pi.turathai.turathaibackend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.Stop;
import pi.turathai.turathaibackend.repository.StopRepo;

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
}