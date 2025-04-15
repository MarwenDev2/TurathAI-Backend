package pi.turathai.turathaibackend.Services;

import pi.turathai.turathaibackend.Entites.Forum;
import java.util.List;
import java.util.Optional;

public interface IForumService {
    Forum createForum(Forum forum);
    List<Forum> getAllForums();
    Optional<Forum> getForumById(Long id);
    Forum updateForum(Long id, Forum forumDetails);
    void deleteForum(Long id);
}
