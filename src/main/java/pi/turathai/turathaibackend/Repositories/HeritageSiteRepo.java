package pi.turathai.turathaibackend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pi.turathai.turathaibackend.Entites.Category;
import pi.turathai.turathaibackend.Entites.HeritageSite;

import java.util.Optional;

public interface HeritageSiteRepo extends JpaRepository<HeritageSite,Long> {
    Optional<HeritageSite> findById(Long id);
}
