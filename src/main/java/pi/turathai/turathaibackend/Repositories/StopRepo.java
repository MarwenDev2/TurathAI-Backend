package pi.turathai.turathaibackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pi.turathai.turathaibackend.Entites.Stop;

@Repository
public interface StopRepo extends JpaRepository<Stop, Long> {
}