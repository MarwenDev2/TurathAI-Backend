package pi.turathai.turathaibackend.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.HeritageSite;
import pi.turathai.turathaibackend.Entites.User;
import pi.turathai.turathaibackend.Entites.Wishlist;
import pi.turathai.turathaibackend.Repositories.HeritageSiteRepo;
import pi.turathai.turathaibackend.Repositories.UserRepository;
import pi.turathai.turathaibackend.Repositories.WishlistRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistService implements IWishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final HeritageSiteRepo heritageSiteRepository;
    public WishlistService(WishlistRepository wishlistRepository, UserRepository userRepository, HeritageSiteRepo heritageSiteRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.heritageSiteRepository = heritageSiteRepository;
    }

    @Override
    public String addToWishlist(Long userId, Long heritageSiteId) {
        // Check if already in wishlist
        if (wishlistRepository.existsByUserIdAndHeritageSiteId(userId, heritageSiteId)) {
            return "Already in wishlist";
        }

        // Retrieve User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Retrieve Heritage Site
        HeritageSite site = heritageSiteRepository.findById(heritageSiteId)
                .orElseThrow(() -> new RuntimeException("Heritage Site not found"));

        // Create and Save Wishlist Entry
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setHeritageSite(site);
        wishlist.setCreatedAt(Date.valueOf(java.time.LocalDate.now()));

        wishlistRepository.save(wishlist);

        return "Added to wishlist";
    }

    /*** Retrieves all wishlist items for a specific user.
     * @param userId User ID whose wishlist is being fetched
     * @return List of wishlist items*/
    @Override
    public List<Wishlist> getWishlist(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return wishlistRepository.findByUserId(userId);
    }

    /*** Removes an entry from the wishlist based on its ID.
     * @param wishlistId ID of the wishlist entry
     * @return Success or failure message*/
    @Override
    public String removeFromWishlist(Long wishlistId) {
        if (wishlistId == null) {
            return "Wishlist ID cannot be null";
        }

        Optional<Wishlist> wishlist = wishlistRepository.findById(wishlistId);
        if (wishlist.isEmpty()) {
            return "Wishlist item not found";
        }

        wishlistRepository.delete(wishlist.get());
        return "Removed from wishlist";
    }

    /*** Retrieves all wishlists with optional search, date range, and pagination.
     * @param searchTerm Search term for user full name, site name, or location
     * @param startDate Start date for filtering (nullable)
     * @param endDate End date for filtering (nullable)
     * @param pageable Pagination and sorting info
     * @return Paginated list of wishlists
     */
    @Override
    public Page<Wishlist> getAllWishlists(String searchTerm, Date startDate, Date endDate, Pageable pageable) {
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            if (startDate != null && endDate != null) {
                return wishlistRepository.findBySearchTermAndCreatedAtBetween(searchTerm, startDate, endDate, pageable);
            }
            return wishlistRepository.findBySearchTerm(searchTerm, pageable);
        }
        if (startDate != null && endDate != null) {
            return wishlistRepository.findByCreatedAtBetween(startDate, endDate, pageable);
        }
        return wishlistRepository.findAll(pageable);
    }

    /**
     * Removes multiple wishlist entries by IDs.
     * @param wishlistIds List of wishlist entry IDs
     * @return Success or failure message
     */
    @Override
    public String bulkRemoveFromWishlist(List<Long> wishlistIds) {
        if (wishlistIds == null || wishlistIds.isEmpty()) {
            return "No wishlist IDs provided";
        }

        long deletedCount = wishlistIds.stream()
                .filter(id -> wishlistRepository.existsById(id))
                .map(id -> {
                    wishlistRepository.deleteById(id);
                    return id;
                })
                .count();

        return String.format("Removed %d wishlist items", deletedCount);
    }

    /*** Retrieves all wishlists for export.
     * @return List of all wishlists*/
    @Override
    public List<Wishlist> getAllForExport() {
        return wishlistRepository.findAllForExport();
    }
}