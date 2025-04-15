package pi.turathai.turathaibackend.Services;

import pi.turathai.turathaibackend.Entites.Wishlist;

import java.util.List;

public interface IWishlistService {
    String addToWishlist(Wishlist wishlist);
    List<Wishlist> getWishlist(Long userId);
    String removeFromWishlist(Long wishlistId);
}