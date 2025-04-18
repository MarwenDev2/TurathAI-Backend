package pi.turathai.turathaibackend.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Entites.Forum;
import pi.turathai.turathaibackend.Services.IForumService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins= "http://Localhost:4200")
@RestController
@RequestMapping("/api/forums")
@RequiredArgsConstructor
public class ForumController {

    private final IForumService forumService;

    @GetMapping
    public List<Forum> getAllForums() {
        return forumService.getAllForums();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Forum> getForumById(@PathVariable Long id) {
        Optional<Forum> forum = forumService.getForumById(id);
        return forum.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Forum> createForum(@RequestBody Forum forum) {
        Forum createdForum = forumService.createForum(forum);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdForum);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Forum> updateForum(@PathVariable Long id, @RequestBody Forum forum) {
        Forum updatedForum = forumService.updateForum(id, forum);
        return ResponseEntity.ok(updatedForum);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteForum(@PathVariable Long id) {
        forumService.deleteForum(id);
    }
}
