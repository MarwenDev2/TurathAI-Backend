package pi.turathai.turathaibackend.Services;

import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.Wishlist;
import pi.turathai.turathaibackend.Repositories.WishlistRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistService implements IWishlistService {

    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    /**
     * Adds an entry to the wishlist. Prevents duplicate entries.
     * @param wishlist Wishlist entity containing user & heritage site
     * @return Success or failure message
     */
    @Override
    public String addToWishlist(Wishlist wishlist) {
        // Check if the entry already exists to prevent duplication
        if (wishlistRepository.existsByUserIdAndHeritageSiteId(
                wishlist.getUser().getId(), wishlist.getHeritageSite().getId())) {
            return "Already in wishlist";
        }

        // Set the created date before saving
        wishlist.setCreatedAt(new Date(System.currentTimeMillis()));

        // Save the wishlist entry
        wishlistRepository.save(wishlist);
        return "Added to wishlist";
    }

    /**
     * Retrieves all wishlist items for a specific user.
     * @param userId User ID whose wishlist is being fetched
     * @return List of wishlist items
     */
    @Override
    public List<Wishlist> getWishlist(Long userId) {
        return wishlistRepository.findAll().stream()
                .filter(w -> w.getUser() != null && userId.equals(w.getUser().getId())) // Ensure null safety
                .toList();
    }

    /**
     * Removes an entry from the wishlist based on its ID.
     * @param wishlistId ID of the wishlist entry
     * @return Success or failure message
     */
    @Override
    public String removeFromWishlist(Long wishlistId) {
        Optional<Wishlist> wishlist = wishlistRepository.findById(wishlistId);

        if (wishlist.isEmpty()) {
            return "Wishlist item not found";
        }

        wishlistRepository.delete(wishlist.get());
        return "Removed from wishlist";
    }
}
