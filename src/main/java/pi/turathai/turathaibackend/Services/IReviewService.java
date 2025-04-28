package pi.turathai.turathaibackend.Services;

import pi.turathai.turathaibackend.Entites.Review;

import java.util.List;
import java.util.Optional;

public interface IReviewService {


    // Add these new methods
    List<Review> getAllReviews();
    Optional<Review> getReviewById(Long id);
    Review updateReview(Long id, Review reviewDetails);
    void deleteReview(Long id);
    List<Review> getReviewsByUser(Long userId);
    /**
     * Adds a review for a heritage site if not already added by the user.
     * @param review Review object containing the rating, comment, etc.
     * @return Success or failure message
     */
    String addReview(Review review);

    /**
     * Retrieves all reviews for a specific heritage site.
     * @param heritageSiteId The ID of the heritage site
     * @return List of reviews for the heritage site
     */
    List<Review> getReviewsByHeritageSite(Long heritageSiteId);

    /**
     * Calculates the average rating for a heritage site.
     * @param heritageSite The heritage site for which we are calculating the average rating
     * @return Average rating of the heritage site
     */
    double calculateAverageRating(Long heritageSite);

    /**
     * Retrieves flagged reviews.
     * @return List of flagged reviews
     */
    List<Review> getFlaggedReviews();

    /**
     * Retrieves reviews with a rating greater than or equal to the specified rating.
     * @param rating Rating threshold
     * @return List of reviews
     */
    List<Review> getReviewsByRating(int rating);

    /**
     * Retrieves reviews that contain a specific keyword in the comment.
     * @param keyword Keyword to search in the comment
     * @return List of reviews
     */
    List<Review> getReviewsByKeywordInComment(String keyword);

    /**
     * Removes a review from a heritage site.
     * @param userId The user ID who made the review
     * @param heritageSiteId The heritage site ID
     * @return Success or failure message
     */
    String removeReview(Long userId, Long heritageSiteId);

    List<Review> getReviewsByUserName(String name);

    List<Review> getReviewsWithFilters(Long heritageSiteId, Integer minRating, String userName, String keyword);
}

