package pi.turathai.turathaibackend.Services;

import pi.turathai.turathaibackend.DTO.CommentDTO;
import pi.turathai.turathaibackend.DTO.ForumDTO;
import pi.turathai.turathaibackend.Entites.Comment;
import pi.turathai.turathaibackend.Entites.Forum;

import java.util.List;
import java.util.Optional;

public interface IForumService {
    Comment addCommentToForum(Long forumId, CommentDTO commentDTO); // Prend un DTO en entrée, retourne l'entité
    Forum createForum(ForumDTO forumDTO);                            // Prend un DTO en entrée, retourne l'entité
    List<Forum> getAllForums();                                       // Retourne la liste d'entités
    Optional<Forum> getForumById(Long id);                            // Retourne l'entité optionnelle
    Forum updateForum(Long id, ForumDTO forumDTO);                    // Prend un DTO en entrée, retourne l'entité
    void deleteForum(Long id);                                        // Pas de problème ici
}
