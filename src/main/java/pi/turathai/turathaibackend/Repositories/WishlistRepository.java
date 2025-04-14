package pi.turathai.turathaibackend.Repositories;

import pi.turathai.turathaibackend.Entites.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    /**
     * Checks if a wishlist entry already exists for a given user and heritage site.
     */
    boolean existsByUserIdAndHeritageSiteId(Long userId, Long heritageSiteId);
}
