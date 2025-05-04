package pi.turathai.turathaibackend.DTO;

import lombok.Data;
import pi.turathai.turathaibackend.Entites.User;

import java.util.Date;


@Data
public class CommentDTO {
    private String content;
    private String image;
    private Long userId;
    private Long forumId;
    private Date createdAt;
    private User user;
    private int liked;
    private int disliked;
}