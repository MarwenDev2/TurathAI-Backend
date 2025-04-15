package pi.turathai.turathaibackend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pi.turathai.turathaibackend.Entites.Business;

import java.util.List;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    List<Business> findByHeritageSiteId(Long heritageSiteId);
}