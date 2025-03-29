package pi.turathai.turathaibackend.Services;

import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.Forum;
import pi.turathai.turathaibackend.Repositories.ForumRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ForumService {

    private final ForumRepository forumRepository;

    public ForumService(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    public List<Forum> getAllForums() {
        return forumRepository.findAll();
    }

    public Forum getForumById(Long id) {
        return forumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Forum non trouvé avec ID : " + id));
    }
    public Forum createForum(Forum forum) {
        if (forum.getCreatedAt() == null) {
            forum.setCreatedAt(LocalDateTime.now());
        }
        return forumRepository.save(forum);
    }


    public Forum updateForum(Long id, Forum forumDetails) {
        Forum forum = getForumById(id);
        forum.setTitle(forumDetails.getTitle());
        forum.setDescription(forumDetails.getDescription());
        forum.setImage(forumDetails.getImage());
        return forumRepository.save(forum);
    }

    public void deleteForum(Long id) {
        Forum forum = getForumById(id);
        forumRepository.delete(forum);
    }
}
