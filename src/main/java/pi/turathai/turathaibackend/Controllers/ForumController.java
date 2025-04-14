package pi.turathai.turathaibackend.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Entites.Forum;
import pi.turathai.turathaibackend.Services.IForumService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/forums")
@RequiredArgsConstructor
public class ForumController {

    private final IForumService forumService;

    @PostMapping
    public Forum createForum(@RequestBody Forum forum) {
        return forumService.createForum(forum);
    }

    @GetMapping
    public List<Forum> getAllForums() {
        return forumService.getAllForums();
    }

    @GetMapping("/{id}")
    public Optional<Forum> getForumById(@PathVariable Long id) {
        return forumService.getForumById(id);
    }

    @PutMapping("/{id}")
    public Forum updateForum(@PathVariable Long id, @RequestBody Forum forumDetails) {
        return forumService.updateForum(id, forumDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteForum(@PathVariable Long id) {
        forumService.deleteForum(id);
    }
}
