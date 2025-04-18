package pi.turathai.turathaibackend.Controllers;

import pi.turathai.turathaibackend.Entites.Wishlist;
import pi.turathai.turathaibackend.Services.IWishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins= "http://Localhost:4200")
@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final IWishlistService wishlistService;

    public WishlistController(IWishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    /**
     * API to add an item to the wishlist.
     * @param (wishlist Wishlist object ) received in the request body
     * @return Response : message
     */
    @PostMapping("/add")
    public ResponseEntity<String> addToWishlist(@RequestBody Wishlist wishlist) {
        return ResponseEntity.ok(wishlistService.addToWishlist(wishlist));
    }

    /**
     * API to get the wishlist of a user.
     * @param : userId User ID whose wishlist is being fetched
     * @return List of wishlist items
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Wishlist>> getWishlist(@PathVariable Long userId) {
        return ResponseEntity.ok(wishlistService.getWishlist(userId));
    }

    /**
     * API to remove an item from the wishlist.
     * @param wishlistId ID of the wishlist entry
     * @return Response message
     */
    @DeleteMapping("/remove/{wishlistId}")
    public ResponseEntity<String> removeFromWishlist(@PathVariable Long wishlistId) {
        return ResponseEntity.ok(wishlistService.removeFromWishlist(wishlistId));
    }
}
