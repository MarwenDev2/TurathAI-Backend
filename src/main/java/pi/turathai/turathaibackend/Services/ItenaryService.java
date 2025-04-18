package pi.turathai.turathaibackend.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.Itinery;
import pi.turathai.turathaibackend.Repositories.ItenaryRepo;

import java.util.List;

@Service
@Slf4j
public class ItenaryService implements IItineryService {

    @Autowired
    private ItenaryRepo itenaryRepository;

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
}