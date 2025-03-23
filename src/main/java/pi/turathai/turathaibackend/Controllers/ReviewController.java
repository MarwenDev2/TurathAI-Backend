package pi.turathai.turathaibackend.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Entites.Review;
import pi.turathai.turathaibackend.Services.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * Adds a review for a heritage site.
     * @param review Review object containing the rating, comment, etc.
     * @return Success or failure message
     */
    @PostMapping
    public ResponseEntity<String> addReview(@RequestBody Review review) {
        String message = reviewService.addReview(review);
        return new ResponseEntity<>(message, message.equals("Review added successfully.") ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }

    /**
     * Retrieves all reviews for a specific heritage site.
     * @param heritageSiteId The ID of the heritage site
     * @return List of reviews for the heritage site
     */
    @GetMapping("/heritage-site/{heritageSiteId}")
    public ResponseEntity<List<Review>> getReviewsByHeritageSite(@PathVariable Long heritageSiteId) {
        List<Review> reviews = reviewService.getReviewsByHeritageSite(heritageSiteId);
        return reviews.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    /**
     * Calculates the average rating for a heritage site.
     * @param heritageSiteId The ID of the heritage site
     * @return Average rating of the heritage site
     */
    @GetMapping("/heritage-site/{heritageSiteId}/average-rating")
    public ResponseEntity<Double> calculateAverageRating(@PathVariable Long heritageSiteId) {
        double averageRating = reviewService.calculateAverageRating(heritageSiteId);
        return new ResponseEntity<>(averageRating, HttpStatus.OK);
    }

    /**
     * Retrieves all flagged reviews.
     * @return List of flagged reviews
     */
    @GetMapping("/flagged")
    public ResponseEntity<List<Review>> getFlaggedReviews() {
        List<Review> flaggedReviews = reviewService.getFlaggedReviews();
        return flaggedReviews.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(flaggedReviews, HttpStatus.OK);
    }

    /**
     * Retrieves reviews with a rating greater than or equal to the specified rating.
     * @param rating Rating threshold
     * @return List of reviews
     */
    @GetMapping("/byRating/{rating}")
    public ResponseEntity<List<Review>> getReviewsByRating(@PathVariable int rating) {
        List<Review> reviews = reviewService.getReviewsByRating(rating);
        return reviews.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    /**
     * Retrieves reviews containing a specific keyword in the comment.
     * @param keyword Keyword to search in the comment
     * @return List of reviews
     */
    @GetMapping("/bycomments")
    public ResponseEntity<List<Review>> getReviewsByKeywordInComment(@RequestParam String keyword) {
        List<Review> reviews = reviewService.getReviewsByKeywordInComment(keyword);
        return reviews.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    /**
     * Removes a review by userId and heritageSiteId.
     * @param userId The user ID who made the review
     * @param heritageSiteId The heritage site ID
     * @return Success or failure message
     */
    @DeleteMapping("/{userId}/heritage-site-remove/{heritageSiteId}")
    public ResponseEntity<String> removeReview(@PathVariable Long userId, @PathVariable Long heritageSiteId) {
        String message = reviewService.removeReview(userId, heritageSiteId);
        return message.equals("Review removed successfully.") ? new ResponseEntity<>(message, HttpStatus.OK) : new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
