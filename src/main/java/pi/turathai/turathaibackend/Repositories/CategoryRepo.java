package pi.turathai.turathaibackend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pi.turathai.turathaibackend.Entites.Category;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category,Long> {
    Optional<Category> findByName(String name);

    Optional<Category> findById(Long id);
}
