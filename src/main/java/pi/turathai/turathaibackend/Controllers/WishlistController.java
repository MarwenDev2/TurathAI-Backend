package pi.turathai.turathaibackend.Controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Entites.Wishlist;
import pi.turathai.turathaibackend.Services.IWishlistService;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final IWishlistService wishlistService;

    public WishlistController(IWishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/add/{userId}/{heritageSiteId}")
    public ResponseEntity<String> addToWishlist(
            @PathVariable Long userId,
            @PathVariable Long heritageSiteId) {
        return ResponseEntity.ok(wishlistService.addToWishlist(userId, heritageSiteId));
    }


    /*** Gets the wishlist of a user.
     * @param userId User ID whose wishlist is being fetched
     * @return List of wishlist items*/
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Wishlist>> getWishlist(@PathVariable Long userId) {
        return ResponseEntity.ok(wishlistService.getWishlist(userId));
    }

    /*** Removes an item from the wishlist.
     * @param wishlistId ID of the wishlist entry
     * @return Response message as JSON*/
    @DeleteMapping("/remove/{wishlistId}")
    public ResponseEntity<Map<String, String>> removeFromWishlist(@PathVariable Long wishlistId) {
        String message = wishlistService.removeFromWishlist(wishlistId);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    /**
     * Gets all wishlists with optional search, date range, pagination, and sorting.
     * @param searchTerm Optional search term (user full name, site name, location)
     * @param startDate Optional start date for filtering
     * @param endDate Optional end date for filtering
     * @param page Page number (default: 0)
     * @param size Page size (default: 10)
     * @param sortBy Field to sort by (default: createdAt)
     * @param sortDir Sort direction (asc/desc, default: desc)
     * @return Paginated list of wishlists
     */
    @GetMapping
    public ResponseEntity<Page<Wishlist>> getAllWishlists(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Wishlist> wishlists = wishlistService.getAllWishlists(searchTerm, startDate, endDate, pageable);
        return ResponseEntity.ok(wishlists);
    }

    /*** Removes multiple wishlist entries.
     * @param wishlistIds List of wishlist entry IDs
     * @return Response message as JSON
     */
    @PostMapping("/bulk-remove")
    public ResponseEntity<Map<String, String>> bulkRemoveFromWishlist(@RequestBody List<Long> wishlistIds) {
        String message = wishlistService.bulkRemoveFromWishlist(wishlistIds);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.ok(response);
    }
    /*** Exports all wishlists as a list (for CSV).
     * @return List of all wishlists
     */
    @GetMapping("/export")
    public ResponseEntity<List<Wishlist>> exportWishlists() {
        return ResponseEntity.ok(wishlistService.getAllForExport());
    }
}