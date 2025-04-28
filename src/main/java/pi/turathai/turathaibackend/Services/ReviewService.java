package pi.turathai.turathaibackend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.Review;
import pi.turathai.turathaibackend.Entites.User;
import pi.turathai.turathaibackend.Repositories.ReviewRepository;
import pi.turathai.turathaibackend.Repositories.UserRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;

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

    // New method for paginated reviews
    public Map<String, Object> getReviewsByUserPaginated(Long userId, int page, int pageSize, String comment, String date, Integer rating) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        LocalDate parsedDate = (date != null && !date.isEmpty()) ? LocalDate.parse(date) : null;
        Page<Review> reviewPage = reviewRepository.findByUserIdWithFilters(
                userId,
                comment != null && !comment.isEmpty() ? comment : null,
                parsedDate,
                rating,
                pageable
        );

        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviewPage.getContent());
        response.put("total", reviewPage.getTotalElements());
        return response;
    }

    public Double getAverageRatingByUser(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        if (reviews.isEmpty()) {
            return 0.0;
        }
        double total = reviews.stream().mapToDouble(Review::getRating).sum();
        return total / reviews.size();
    }

    @Override
    public String addReview(Review review) {

        reviewRepository.save(review);
        return "Review added successfully.";
    }

    //compute the rating distribution
    public Map<Integer, Long> getRatingDistributionByUser(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        Map<Integer, Long> distribution = new HashMap<>();
        // Initialize counts for ratings 1 to 5
        for (int i = 1; i <= 5; i++) {
            distribution.put(i, 0L);
        }
        // Count reviews for each rating
        reviews.stream()
                .collect(Collectors.groupingBy(Review::getRating, Collectors.counting()))
                .forEach((rating, count) -> distribution.put(rating.intValue(), count));
        return distribution;
    }

    @Override
    public Review updateReview(Long id, Review reviewDetails) {
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if (optionalReview.isPresent()) {
            Review existingReview = optionalReview.get();
            existingReview.setRating(reviewDetails.getRating());
            existingReview.setComment(reviewDetails.getComment());
            existingReview.setFlagged(reviewDetails.isFlagged());
            // Update the heritage site by setting idSite
            if (reviewDetails.getHeritageSite() != null) {
                existingReview.setHeritageSite(reviewDetails.getHeritageSite());
            }
            return reviewRepository.save(existingReview);
        } else {
            throw new RuntimeException("Review not found with id " + id);
        }
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