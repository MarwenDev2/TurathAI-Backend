package pi.turathai.turathaibackend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pi.turathai.turathaibackend.Entites.Itinery;
import pi.turathai.turathaibackend.Entites.User;

import java.util.List;


@Repository
public interface ItenaryRepo extends JpaRepository<Itinery, Long> {

    List<Itinery> findByUser(User user);


}

