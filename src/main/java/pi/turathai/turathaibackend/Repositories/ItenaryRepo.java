package pi.turathai.turathaibackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pi.turathai.turathaibackend.Entites.Itinery;




@Repository
public interface ItenaryRepo extends JpaRepository<Itinery, Long> {
}

