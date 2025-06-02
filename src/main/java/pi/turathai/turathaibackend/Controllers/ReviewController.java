package pi.turathai.turathaibackend.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Entites.Review;
import pi.turathai.turathaibackend.Repositories.ReviewRepository;
import pi.turathai.turathaibackend.Services.ReviewService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final  ReviewRepository reviewRepository;

    @PostMapping
    public ResponseEntity<Map<String, String>> addReview(@RequestBody Review review) {
        reviewService.addReview(review);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Review added successfully");
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // New endpoint for paginated reviews
    @GetMapping("/user/{userId}/paginated")
    public ResponseEntity<Map<String, Object>> getReviewsByUserPaginated(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(required = false) String comment,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) Integer rating) {
        Map<String, Object> response = reviewService.getReviewsByUserPaginated(userId, page, pageSize, comment, date, rating);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/user/{userId}/average-rating")
    public ResponseEntity<Double> getAverageRatingByUser(@PathVariable Long userId) {
        Double averageRating = reviewService.getAverageRatingByUser(userId);
        return ResponseEntity.ok(averageRating);
    }

    @GetMapping("/user/{userId}/rating-distribution")
    public ResponseEntity<Map<Integer, Long>> getRatingDistributionByUser(@PathVariable Long userId) {
        Map<Integer, Long> distribution = reviewService.getRatingDistributionByUser(userId);
        return ResponseEntity.ok(distribution);
    }

    // Endpoint for recent reviews
    @GetMapping("/user/{userId}/recent")
    public ResponseEntity<List<Review>> getRecentReviewsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "5") int limit) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(0, limit, sort);
        Page<Review> recentReviews = reviewRepository.findByUserId(userId, pageable);
        return ResponseEntity.ok(recentReviews.getContent());
    }

    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id, @RequestBody Review reviewDetails) {
        return reviewService.updateReview(id, reviewDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }

    @DeleteMapping("/{userId}/heritage-site-remove/{heritageSiteId}")
    public String removeUserReview(@PathVariable Long userId, @PathVariable Long heritageSiteId) {
        return reviewService.removeReview(userId, heritageSiteId);
    }

    @GetMapping("/heritage-site/{heritageSiteId}")
    public List<Review> getReviewsByHeritageSite(@PathVariable Long heritageSiteId) {
        return reviewService.getReviewsByHeritageSite(heritageSiteId);
    }

    @GetMapping("/user/{userId}")
    public List<Review> getReviewsByUser(@PathVariable Long userId) {
        return reviewService.getReviewsByUser(userId);
    }

    @GetMapping("/heritage-site/{heritageSiteId}/average-rating")
    public double calculateAverageRating(@PathVariable Long heritageSiteId) {
        return reviewService.calculateAverageRating(heritageSiteId);
    }

    @GetMapping("/flagged")
    public List<Review> getFlaggedReviews() {
        return reviewService.getFlaggedReviews();
    }

    @GetMapping("/byRating/{rating}")
    public List<Review> getReviewsByRating(@PathVariable int rating) {
        return reviewService.getReviewsByRating(rating);
    }

    @GetMapping("/bycomments")
    public List<Review> getReviewsByKeywordInComment(@RequestParam String keyword) {
        return reviewService.getReviewsByKeywordInComment(keyword);
    }

    @GetMapping("/byUserName")
    public List<Review> getReviewsByUserName(@RequestParam String name) {
        return reviewService.getReviewsByUserName(name);
    }

    @GetMapping("/filter")
    public List<Review> getReviewsWithFilters(
            @RequestParam(required = false) Long heritageSiteId,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String keyword
    ) {
        return reviewService.getReviewsWithFilters(heritageSiteId, minRating, userName, keyword);
    }




}