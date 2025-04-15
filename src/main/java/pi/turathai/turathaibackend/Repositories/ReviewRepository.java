package pi.turathai.turathaibackend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pi.turathai.turathaibackend.Entites.Review;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Custom query to check if a user has reviewed a specific heritage site
    boolean existsByUserIdAndHeritageSiteId(Long userId, Long heritageSiteId);

    // Custom query to find a review by userId and heritageSiteId
    Optional<Review> findByUserIdAndHeritageSiteId(Long userId, Long heritageSiteId);

    // Custom method to delete a review by userId and heritageSiteId
    void deleteByUserIdAndHeritageSiteId(Long userId, Long heritageSiteId);

    // Custom query to find reviews by rating (greater than or equal)
    List<Review> findByRatingGreaterThanEqual(int rating);

    // Custom query to find flagged reviews (flagged reviews have a 'true' value)
    List<Review> findByFlaggedTrue();

    // Custom query to find reviews containing a certain word or phrase in the comment
    List<Review> findByCommentContaining(String keyword);

    // You could also add sorting features to the queries if needed
    List<Review> findByHeritageSiteIdOrderByRatingDesc(Long heritageSiteId);
}
