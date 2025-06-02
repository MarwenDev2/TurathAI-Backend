package pi.turathai.turathaibackend.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.DTO.CommentDTO;
import pi.turathai.turathaibackend.DTO.ForumDTO;
import pi.turathai.turathaibackend.Entites.Comment;
import pi.turathai.turathaibackend.Entites.Forum;
import pi.turathai.turathaibackend.Services.CommentService;
import pi.turathai.turathaibackend.Services.IForumService;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins= "*")
@RestController
@RequestMapping("/api/forums")
@RequiredArgsConstructor
public class ForumController {

    private final IForumService forumService;

    private final CommentService commentService;

    @GetMapping
    public List<Forum> getAllForums() {
        return forumService.getAllForums();
    }


    @PostMapping
    public ResponseEntity<Forum> createForum(@RequestBody ForumDTO forumDTO) {
        // Set current timestamp if not provided
        if (forumDTO.getCreatedAt() == null) {
            forumDTO.setCreatedAt(LocalDateTime.now());
        }
        Forum createdForum = forumService.createForum(forumDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdForum);
    }

    @PostMapping("/{forumId}/comments")
    public ResponseEntity<Comment> addCommentToForum(
            @PathVariable Long forumId,
            @RequestBody CommentDTO commentDTO) {

        // Set the forumId from path variable
        commentDTO.setForumId(forumId);

        // Create the comment
        Comment comment = commentService.createComment(
                commentDTO.getContent(),
                commentDTO.getImage(), commentDTO.getUserId(),
                commentDTO.getForumId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }



    @GetMapping("/{id}")
    public ResponseEntity<Forum> getForumById(@PathVariable Long id) {
        Optional<Forum> forum = forumService.getForumById(id);
        return forum.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Forum> updateForum(@PathVariable Long id, @RequestBody ForumDTO forumDTO) {
        Forum updatedForum = forumService.updateForum(id, forumDTO);
        return ResponseEntity.ok(updatedForum);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteForum(@PathVariable Long id) {
        forumService.deleteForum(id);
    }
}