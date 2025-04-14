package pi.turathai.turathaibackend.Services;

import pi.turathai.turathaibackend.Entites.Comment;
import java.util.List;
import java.util.Optional;

public interface ICommentService {
    Comment createComment(Comment comment);
    List<Comment> getAllComments();
    Optional<Comment> getCommentById(Long id);
    List<Comment> getCommentsByForum(Long forumId);
    void deleteComment(Long id);
}
