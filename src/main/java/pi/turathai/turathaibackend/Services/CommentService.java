package pi.turathai.turathaibackend.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.DTO.CommentDTO;
import pi.turathai.turathaibackend.Entites.Comment;
import pi.turathai.turathaibackend.Entites.Forum;
import pi.turathai.turathaibackend.Entites.User;
import pi.turathai.turathaibackend.Repositories.CommentRepository;
import pi.turathai.turathaibackend.Repositories.ForumRepository;
import pi.turathai.turathaibackend.Repositories.UserRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ForumRepository forumRepository;
    private final BadWordService badWordService;

    // Méthode existante si tu veux continuer à accepter un objet Comment brut
    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    // Nouvelle méthode qui crée un commentaire avec associations explicites
    public Comment createComment(String content, String image, Long userId, Long forumId) {
        final Long finalUserId = (userId == null) ? 1L : userId;
        final Long finalForumId = (forumId == null) ? 66L : forumId;

        User user = userRepository.findById(finalUserId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec ID: " + finalUserId));

        Forum forum = forumRepository.findById(finalForumId)
                .orElseThrow(() -> new RuntimeException("Forum non trouvé avec ID: " + finalForumId));

        // Vérification des badwords via le BadWordService
        if (badWordService.containsBadWords(content)) {
            throw new RuntimeException("Le commentaire contient des mots interdits.");
        }

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

    @Override

    public Comment updateComment(Long id, CommentDTO dto) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commentaire non trouvé avec ID: " + id));

        comment.setContent(dto.getContent());
        comment.setImage(dto.getImage());
        comment.setLiked(dto.getLiked());
        comment.setDisliked(dto.getDisliked());

        // Si tu veux aussi autoriser la modification du user ou du forum (optionnel) :
        Long userId = dto.getUserId() != null ? dto.getUserId() : 1L;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec ID: " + userId));
        comment.setUser(user);

        if (dto.getForumId() != null) {
            Forum forum = forumRepository.findById(dto.getForumId())
                    .orElseThrow(() -> new RuntimeException("Forum non trouvé avec ID: " + dto.getForumId()));
            comment.setForum(forum);
        }

        return commentRepository.save(comment);
    }



}