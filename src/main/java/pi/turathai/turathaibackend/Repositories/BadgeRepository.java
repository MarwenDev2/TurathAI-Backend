package pi.turathai.turathaibackend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pi.turathai.turathaibackend.Entites.Badge;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {
    //Checks if a badge name already exists before creation.
    boolean existsByName(String name);
}
