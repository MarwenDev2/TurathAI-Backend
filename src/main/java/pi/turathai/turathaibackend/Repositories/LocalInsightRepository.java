package pi.turathai.turathaibackend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pi.turathai.turathaibackend.Entites.LocalInsight;
@Repository
public interface LocalInsightRepository extends JpaRepository<LocalInsight, Long> {
}
