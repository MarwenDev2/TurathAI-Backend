package pi.turathai.turathaibackend.Repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pi.turathai.turathaibackend.Entites.Review;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;



@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Add these new methods
    List<Review> findByUserId(Long userId);

    // New method for paginated reviews by user
    @Query("SELECT r FROM Review r WHERE r.user.id = :userId " +
            "AND (:comment IS NULL OR LOWER(r.comment) LIKE LOWER(CONCAT('%', :comment, '%'))) " +
            "AND (:date IS NULL OR DATE(r.createdAt) = DATE(:date)) " +
            "AND (:rating IS NULL OR r.rating = :rating)")
    Page<Review> findByUserIdWithFilters(
            @Param("userId") Long userId,
            @Param("comment") String comment,
            @Param("date") LocalDate date,
            @Param("rating") Integer rating,
            Pageable pageable
    );

    List<Review> findByHeritageSiteId(Long heritageSiteId);

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

    List<Review> findByUserFirstNameContainingIgnoreCaseOrUserLastNameContainingIgnoreCase(String firstName, String lastName);

    // New method for mixed filtering
    @Query("SELECT r FROM Review r " +
            "WHERE (:heritageSiteId IS NULL OR r.heritageSite.id = :heritageSiteId) " +
            "AND (:minRating IS NULL OR r.rating >= :minRating) " +
            "AND (:userName IS NULL OR LOWER(r.user.firstName) LIKE LOWER(CONCAT('%', :userName, '%')) " +
            "   OR LOWER(r.user.lastName) LIKE LOWER(CONCAT('%', :userName, '%'))) " +
            "AND (:keyword IS NULL OR LOWER(r.comment) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Review> findReviewsWithFilters(
            @Param("heritageSiteId") Long heritageSiteId,
            @Param("minRating") Integer minRating,
            @Param("userName") String userName,
            @Param("keyword") String keyword);

    // New method for fetching reviews by userId with pagination
    @Query("SELECT r FROM Review r WHERE r.user.id = :userId")
    Page<Review> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
