package pi.turathai.turathaibackend.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.Forum;
import pi.turathai.turathaibackend.Repositories.ForumRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForumService implements IForumService {

    private final ForumRepository forumRepository;

    @Override
    public Forum createForum(Forum forum) {
        if (forum.getCreatedAt() == null) {
            forum.setCreatedAt(LocalDateTime.now());
        }
        return forumRepository.save(forum);
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
    public Forum updateForum(Long id, Forum forumDetails) {
        return forumRepository.findById(id).map(forum -> {
            forum.setTitle(forumDetails.getTitle());
            forum.setDescription(forumDetails.getDescription());
            forum.setImage(forumDetails.getImage());
            return forumRepository.save(forum);
        }).orElseThrow(() -> new RuntimeException("Forum non trouvé avec ID : " + id));
    }

    @Override
    public void deleteForum(Long id) {
        forumRepository.findById(id).ifPresent(forumRepository::delete);
    }
}
