package pi.turathai.turathaibackend.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pi.turathai.turathaibackend.Entites.Wishlist;

import java.sql.Date;
import java.util.List;

public interface IWishlistService {
    String addToWishlist(Wishlist wishlist);
    List<Wishlist> getWishlist(Long userId);
    String removeFromWishlist(Long wishlistId);
    Page<Wishlist> getAllWishlists(String searchTerm, Date startDate, Date endDate, Pageable pageable);
    String bulkRemoveFromWishlist(List<Long> wishlistIds);
    List<Wishlist> getAllForExport();
}