package pi.turathai.turathaibackend.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.Comment;
import pi.turathai.turathaibackend.Entites.Forum;
import pi.turathai.turathaibackend.Entites.User;
import pi.turathai.turathaibackend.Repositories.CommentRepository;
import pi.turathai.turathaibackend.Repositories.ForumRepository;
import pi.turathai.turathaibackend.Repositories.UserRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ForumRepository forumRepository;

    // Méthode existante si tu veux continuer à accepter un objet Comment brut
    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    // Nouvelle méthode qui crée un commentaire avec associations explicites
    public Comment createComment(String content, String image, Long userId, Long forumId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec ID: " + userId));

        Forum forum = forumRepository.findById(forumId)
                .orElseThrow(() -> new RuntimeException("Forum non trouvé avec ID: " + forumId));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setImage(image);
        comment.setCreatedAt(new Date(System.currentTimeMillis()));
        comment.setLiked(0);
        comment.setDisliked(0);
        comment.setUser(user);
        comment.setForum(forum);

        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> getCommentsByForum(Long forumId) {
        return commentRepository.findByForumId(forumId);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
