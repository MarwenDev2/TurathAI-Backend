package pi.turathai.turathaibackend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pi.turathai.turathaibackend.Entites.Image;

public interface ImageRepo extends JpaRepository<Image , Long> {
}
