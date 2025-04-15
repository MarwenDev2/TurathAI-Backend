package pi.turathai.turathaibackend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pi.turathai.turathaibackend.Entites.Category;

public interface CategoryRepo extends JpaRepository<Category,Long> {
}
