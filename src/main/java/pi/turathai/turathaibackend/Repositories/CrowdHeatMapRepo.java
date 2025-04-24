package pi.turathai.turathaibackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pi.turathai.turathaibackend.Entites.CrowdHeatMap;

@Repository
public interface CrowdHeatMapRepo extends JpaRepository<CrowdHeatMap, Long> {
}