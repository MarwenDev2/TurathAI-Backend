package pi.turathai.turathaibackend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pi.turathai.turathaibackend.Entites.Earned;

import java.util.List;

@Repository
public interface EarnedRepository extends JpaRepository<Earned, Long> {
    List<Earned> findByUserId(Long userId);
    List<Earned> findByBadgeId(Long badgeId);
    boolean existsByUserIdAndBadgeId(Long userId, Long badgeId);
}
