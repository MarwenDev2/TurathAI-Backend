package pi.turathai.turathaibackend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pi.turathai.turathaibackend.Entites.Stop;

import java.util.List;

@Repository
public interface StopRepo extends JpaRepository<Stop, Long> {

    List<Stop> findByItineryId(long itineryId);

    List<Stop> findByHeritageSiteId(Long siteId);
}