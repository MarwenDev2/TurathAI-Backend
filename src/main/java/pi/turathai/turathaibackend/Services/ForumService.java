package pi.turathai.turathaibackend.Services;

<<<<<<< HEAD
import lombok.RequiredArgsConstructor;
=======
>>>>>>> a07ebc221fe59784c111634b5e124c10d13a4ec9
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.Forum;
import pi.turathai.turathaibackend.Repositories.ForumRepository;

<<<<<<< HEAD
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForumService implements IForumService {

    private final ForumRepository forumRepository;

    @Override
    public Forum createForum(Forum forum) {
        return forumRepository.save(forum);
    }

    @Override
=======
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ForumService {

    private final ForumRepository forumRepository;

    public ForumService(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

>>>>>>> a07ebc221fe59784c111634b5e124c10d13a4ec9
    public List<Forum> getAllForums() {
        return forumRepository.findAll();
    }

<<<<<<< HEAD
    @Override
    public Optional<Forum> getForumById(Long id) {
        return forumRepository.findById(id);
    }

    @Override
    public Forum updateForum(Long id, Forum forumDetails) {
        return forumRepository.findById(id).map(forum -> {
            forum.setTitle(forumDetails.getTitle());
            forum.setDescription(forumDetails.getDescription());
            forum.setImage(forumDetails.getImage());
            return forumRepository.save(forum);
        }).orElseThrow(() -> new RuntimeException("Forum non trouvé"));
    }

    @Override
    public void deleteForum(Long id) {
        forumRepository.deleteById(id);
=======
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
>>>>>>> a07ebc221fe59784c111634b5e124c10d13a4ec9
    }
}
