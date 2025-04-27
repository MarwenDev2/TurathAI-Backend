package pi.turathai.turathaibackend.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pi.turathai.turathaibackend.Entites.Wishlist;

import java.sql.Date;
import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    /*** Checks if a wishlist entry exists for a user and heritage site*/
    boolean existsByUserIdAndHeritageSiteId(Long userId, Long heritageSiteId);
    /*** Finds wishlists by user ID.*/
    List<Wishlist> findByUserId(Long userId);

    /*** Finds wishlists matching search term in user full name, site name, or location, with pagination.*/
    @Query("SELECT w FROM Wishlist w WHERE " +
            "LOWER(CONCAT(w.user.firstName, ' ', w.user.lastName)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(w.heritageSite.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(w.heritageSite.location) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Wishlist> findBySearchTerm(String searchTerm, Pageable pageable);

    /*** Finds wishlists within a date range, with pagination.*/
    Page<Wishlist> findByCreatedAtBetween(Date startDate, Date endDate, Pageable pageable);

    /*** Finds wishlists by search term and date range, with pagination.*/
    @Query("SELECT w FROM Wishlist w WHERE " +
            "(LOWER(CONCAT(w.user.firstName, ' ', w.user.lastName)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(w.heritageSite.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(w.heritageSite.location) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND " +
            "w.createdAt BETWEEN :startDate AND :endDate")
    Page<Wishlist> findBySearchTermAndCreatedAtBetween(String searchTerm, Date startDate, Date endDate, Pageable pageable);

    /*** Finds all wishlists for export, sorted by user first name and created date.*/
    @Query("SELECT w FROM Wishlist w ORDER BY w.user.firstName, w.createdAt DESC")
    List<Wishlist> findAllForExport();
}