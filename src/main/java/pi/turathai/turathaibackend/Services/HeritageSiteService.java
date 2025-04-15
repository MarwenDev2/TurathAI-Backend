package pi.turathai.turathaibackend.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.HeritageSite;
import pi.turathai.turathaibackend.Services.IHeritageSite;
import pi.turathai.turathaibackend.Repositories.HeritageSiteRepo;

import java.util.List;

@Service
@Slf4j
public class HeritageSiteService implements IHeritageSite {

    private final HeritageSiteRepo heritageSiteRepo;

    @Autowired
    public HeritageSiteService(HeritageSiteRepo heritageSiteRepo) {
        this.heritageSiteRepo = heritageSiteRepo;
    }
    @Override
    public HeritageSite add(HeritageSite heritageSite) {
        log.info("adding heritage site:");
        return heritageSiteRepo.save(heritageSite);
    }

    @Override
    public HeritageSite update(HeritageSite heritageSite) {
        log.info("updating heritage site:");
        return heritageSiteRepo.save(heritageSite);
    }

    @Override
    public void remove(long id) {
        log.info("deleting heritage site with Id : ");
         heritageSiteRepo.deleteById(id);
    }

    @Override
    public HeritageSite getByID(long id) {
        log.info("get heritage site with Id : ");
        return heritageSiteRepo.findById(id).orElse(null);
    }

    @Override
    public List<HeritageSite> getAll() {
        log.info("fetch All Heritage sites : ");
        return heritageSiteRepo.findAll();
    }
}
