package pi.turathai.turathaibackend.Services;

import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.Review;
import pi.turathai.turathaibackend.Repositories.ReviewRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // =============== BASIC CRUD OPERATIONS ===============

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public String addReview(Review review) {

        reviewRepository.save(review);
        return "Review added successfully.";
    }

    @Override
    public Review updateReview(Long id, Review reviewDetails) {
        return reviewRepository.findById(id)
                .map(existingReview -> {
                    // Update only modifiable fields
                    if (reviewDetails.getRating() != 0) {
                        existingReview.setRating(reviewDetails.getRating());
                    }
                    if (reviewDetails.getComment() != null) {
                        existingReview.setComment(reviewDetails.getComment());
                    }
                    existingReview.setFlagged(reviewDetails.isFlagged());
                    return reviewRepository.save(existingReview);
                })
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    // =============== SPECIALIZED OPERATIONS ===============

    @Override
    public List<Review> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    @Override
    public List<Review> getReviewsByHeritageSite(Long heritageSiteId) {
        return reviewRepository.findByHeritageSiteIdOrderByRatingDesc(heritageSiteId);
    }

    @Override
    public double calculateAverageRating(Long heritageSiteId) {
        return reviewRepository.findByHeritageSiteId(heritageSiteId)
                .stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }

    @Override
    public List<Review> getFlaggedReviews() {
        return reviewRepository.findByFlaggedTrue();
    }

    @Override
    public List<Review> getReviewsByRating(int rating) {
        return reviewRepository.findByRatingGreaterThanEqual(rating);
    }

    @Override
    public List<Review> getReviewsByKeywordInComment(String keyword) {
        return reviewRepository.findByCommentContaining(keyword);
    }

    @Override
    public String removeReview(Long userId, Long heritageSiteId) {
        if (reviewRepository.existsByUserIdAndHeritageSiteId(userId, heritageSiteId)) {
            reviewRepository.deleteByUserIdAndHeritageSiteId(userId, heritageSiteId);
            return "Review removed successfully.";
        }
        return "Review not found.";
    }
    @Override
    public List<Review> getReviewsByUserName(String name) {
        return reviewRepository.findByUserFirstNameContainingIgnoreCaseOrUserLastNameContainingIgnoreCase(name, name);
    }


    @Override
    public List<Review> getReviewsWithFilters(Long heritageSiteId, Integer minRating, String userName, String keyword) {
        // Use the custom repository method to apply all filters in a single query
        return reviewRepository.findReviewsWithFilters(
                heritageSiteId,
                minRating,
                userName != null ? userName.trim() : null,
                keyword != null ? keyword.trim() : null
        );
    }
}