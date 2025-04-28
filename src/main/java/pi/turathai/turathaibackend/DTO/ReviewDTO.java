package pi.turathai.turathaibackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;
    private int rating;
    private String comment;
    private Date createdAt;
    private boolean flagged;
    private UserDTO user;
    private HeritageSiteDTO heritageSite;
}
