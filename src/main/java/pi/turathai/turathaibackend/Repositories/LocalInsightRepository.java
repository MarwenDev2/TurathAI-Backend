package pi.turathai.turathaibackend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pi.turathai.turathaibackend.Entites.LocalInsight;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface LocalInsightRepository extends JpaRepository<LocalInsight, Long> {
    @Query("SELECT li.type, COUNT(li) FROM LocalInsight li GROUP BY li.type")
    List<Object[]> countByType();
}



