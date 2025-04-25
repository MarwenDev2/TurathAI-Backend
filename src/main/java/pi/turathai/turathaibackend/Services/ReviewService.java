package pi.turathai.turathaibackend.Services;

import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.Review;
import pi.turathai.turathaibackend.Repositories.ReviewRepository;

import java.util.List;

@Service
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;



    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * Adds a review for a heritage site if not already added by the user.
     * @param review Review object containing the rating, comment, etc.
     * @return Success or failure message
     */
    public String addReview(Review review) {
        Long userId = review.getUser().getId();
        Long heritageSiteId = review.getHeritageSite().getId();

        // Check if the user has already reviewed the heritage site using the new custom query
        if (reviewRepository.existsByUserIdAndHeritageSiteId(userId, heritageSiteId)) {
            return "You have already reviewed this heritage site.";
        }
        reviewRepository.save(review);
        return "Review added successfully.";
    }

    /**
     * Retrieves all reviews for a specific heritage site.
     * @param heritageSiteId The ID of the heritage site
     * @return List of reviews for the heritage site
     */
    public List<Review> getReviewsByHeritageSite(Long heritageSiteId) {
        return reviewRepository.findAll().stream()
                .filter(review -> review.getHeritageSite().getId().equals(heritageSiteId))
                .toList();
    }

    /**
     * Calculates the average rating for a heritage site.
     * @param heritageSite The heritage site for which we are calculating the average rating
     * @return Average rating of the heritage site
     */

    /**
     * Retrieves flagged reviews.
     * @return List of flagged reviews
     */
    public List<Review> getFlaggedReviews() {
        return reviewRepository.findByFlaggedTrue();
    }

    /**
     * Retrieves reviews with a rating greater than or equal to the specified rating.
     * @param rating Rating threshold
     * @return List of reviews
     */
    public List<Review> getReviewsByRating(int rating) {
        return reviewRepository.findByRatingGreaterThanEqual(rating);
    }

    /**
     * Retrieves reviews that contain a specific keyword in the comment.
     * @param keyword Keyword to search in the comment
     * @return List of reviews
     */
    public List<Review> getReviewsByKeywordInComment(String keyword) {
        return reviewRepository.findByCommentContaining(keyword);
    }

    /**
     * Removes a review from a heritage site.
     * @param userId The user ID who made the review
     * @param heritageSiteId The heritage site ID
     * @return Success or failure message
     */
    public String removeReview(Long userId, Long heritageSiteId) {
        // Check if the review exists using the custom query method
        if (reviewRepository.existsByUserIdAndHeritageSiteId(userId, heritageSiteId)) {
            // Delete the review using the custom delete method
            reviewRepository.deleteByUserIdAndHeritageSiteId(userId, heritageSiteId);
            return "Review removed successfully.";
        }

        return "Review not found.";
    }

    public double calculateAverageRating(Long heritageSiteId) {
        // Use the repository method that does the calculation in the database
        Double average = reviewRepository.calculateAverageRatingBySiteId(heritageSiteId);
        return average != null ? average : 0.0;
    }

    // Alternative implementation if you can't modify the repository:
    public double calculateAverageRatingAlternative(Long heritageSiteId) {
        List<Review> reviews = reviewRepository.findAll().stream()
                .filter(review ->
                        review.getHeritageSite() != null &&
                                review.getHeritageSite().getId() != null &&
                                review.getHeritageSite().getId().equals(heritageSiteId))
                .toList();

        if (reviews.isEmpty()) {
            return 0;
        }

        int totalRating = reviews.stream()
                .mapToInt(Review::getRating)
                .sum();
        return (double) totalRating / reviews.size();
    }
}
