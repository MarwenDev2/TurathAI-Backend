package pi.turathai.turathaibackend.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Entites.Forum;
import pi.turathai.turathaibackend.Services.ForumService;

import java.util.List;

@RestController
@RequestMapping("/forums")
public class ForumController {

    private final ForumService forumService;

    public ForumController(ForumService forumService) {
        this.forumService = forumService;
    }

    // ✅ 1. Récupérer tous les forums
    @GetMapping
    public List<Forum> getAllForums() {
        return forumService.getAllForums();
    }

    // ✅ 2. Récupérer un forum par ID
    @GetMapping("/{id}")
    public ResponseEntity<Forum> getForumById(@PathVariable Long id) {
        Forum forum = forumService.getForumById(id);
        return ResponseEntity.ok(forum);
    }

    // ✅ 3. Ajouter un nouveau forum
    @PostMapping
    public ResponseEntity<Forum> createForum(@RequestBody Forum forum) {
        Forum createdForum = forumService.createForum(forum);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdForum);
    }

    // ✅ 4. Modifier un forum existant
    @PutMapping("/{id}")
    public ResponseEntity<Forum> updateForum(@PathVariable Long id, @RequestBody Forum forum) {
        Forum updatedForum = forumService.updateForum(id, forum);
        return ResponseEntity.ok(updatedForum);
    }

    // ✅ 5. Supprimer un forum
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteForum(@PathVariable Long id) {
        forumService.deleteForum(id);
    }
}
