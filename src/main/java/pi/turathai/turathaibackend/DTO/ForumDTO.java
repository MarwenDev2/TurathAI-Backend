package pi.turathai.turathaibackend.DTO;

import lombok.Data;
import pi.turathai.turathaibackend.Entites.User;
import java.time.LocalDateTime;

@Data
public class ForumDTO {
    private String title;
    private String description;
    private String image;
    private LocalDateTime createdAt;
    private User user;  // Changed from Long userId to User object
}