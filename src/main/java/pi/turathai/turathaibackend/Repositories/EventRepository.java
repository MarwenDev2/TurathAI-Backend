package pi.turathai.turathaibackend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pi.turathai.turathaibackend.Entites.Event;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByHeritageSiteId(Long heritageSiteId);

    @Query("SELECT COUNT(e) FROM Event e WHERE e.startDate > :currentDate")
    long countByStartDateAfter(Date currentDate);
}