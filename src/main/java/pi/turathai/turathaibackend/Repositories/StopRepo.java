package pi.turathai.turathaibackend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pi.turathai.turathaibackend.Entites.Stop;

import java.util.List;

@Repository
public interface StopRepo extends JpaRepository<Stop, Long> {


    @Modifying
    @Query("DELETE FROM Stop s WHERE s.itinery.id = :itineraryId")
    void deleteByItineryId(@Param("itineraryId") Long itineraryId);

    List<Stop> findByItineryId(long itineryId);

    List<Stop> findByHeritageSiteId(Long siteId);
}