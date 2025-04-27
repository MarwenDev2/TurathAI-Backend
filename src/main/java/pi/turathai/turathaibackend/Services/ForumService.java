package pi.turathai.turathaibackend.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.DTO.CommentDTO;
import pi.turathai.turathaibackend.DTO.ForumDTO;
import pi.turathai.turathaibackend.Entites.Comment;
import pi.turathai.turathaibackend.Entites.Forum;
import pi.turathai.turathaibackend.Entites.User;
import pi.turathai.turathaibackend.Repositories.CommentRepository;
import pi.turathai.turathaibackend.Repositories.ForumRepository;
import pi.turathai.turathaibackend.Repositories.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForumService implements IForumService {

    private final UserRepository userRepository;
    private final ForumRepository forumRepository;
    private final CommentRepository commentRepository;

    @Override
    public Forum createForum(ForumDTO forumDTO) {
        try {
            Forum forum = new Forum();
            forum.setTitle(forumDTO.getTitle());
            forum.setDescription(forumDTO.getDescription());
            forum.setImage(forumDTO.getImage());
            forum.setCreatedAt(forumDTO.getCreatedAt());

            // Gestion de l'utilisateur dans la création du forum
            if (forumDTO.getUser() != null) {
                User user = userRepository.findById(forumDTO.getUser().getId())
                        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + forumDTO.getUser().getId()));
                forum.setUser(user);
            }

            return forumRepository.save(forum);
        } catch (Exception e) {
            // Capture l'exception et la renvoie avec un message plus explicite
            throw new RuntimeException("Erreur lors de la création du forum : " + e.getMessage(), e);
        }
    }

    @Override
    public Comment addCommentToForum(Long forumId, CommentDTO commentDTO) {
        try {
            Forum forum = forumRepository.findById(forumId)
                    .orElseThrow(() -> new RuntimeException("Forum non trouvé avec l'ID : " + forumId));

            Comment comment = new Comment();
            comment.setContent(commentDTO.getContent());
            comment.setImage(commentDTO.getImage());
            comment.setCreatedAt(new Date(System.currentTimeMillis()));
            comment.setForum(forum);

            // Ajout de l'utilisateur si nécessaire
            if (commentDTO.getUserId() != null) {
                User user = userRepository.findById(commentDTO.getUserId())
                        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + commentDTO.getUserId()));
                comment.setUser(user);
            }

            return commentRepository.save(comment);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'ajout du commentaire : " + e.getMessage(), e);
        }
    }

    @Override
    public List<Forum> getAllForums() {
        return forumRepository.findAll();
    }

    @Override
    public Optional<Forum> getForumById(Long id) {
        return forumRepository.findById(id);
    }



    @Override
    public Forum updateForum(Long id, ForumDTO forumDTO) {
        try {
            return forumRepository.findById(id).map(forum -> {
                forum.setTitle(forumDTO.getTitle());
                forum.setDescription(forumDTO.getDescription());
                forum.setImage(forumDTO.getImage());
                forum.setCreatedAt(forumDTO.getCreatedAt());

                // Mise à jour de l'utilisateur
                if (forumDTO.getUser() != null) {
                    User user = userRepository.findById(forumDTO.getUser().getId())
                            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + forumDTO.getUser().getId()));
                    forum.setUser(user);
                }

                return forumRepository.save(forum);
            }).orElseThrow(() -> new RuntimeException("Forum non trouvé avec l'ID : " + id));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour du forum : " + e.getMessage(), e);
        }
    }


    @Override
    public void deleteForum(Long id) {
        try {
            forumRepository.findById(id).ifPresent(forumRepository::delete);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression du forum avec l'ID : " + id, e);
        }
    }
}