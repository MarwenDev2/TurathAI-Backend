package pi.turathai.turathaibackend.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.DTO.CommentDTO;
import pi.turathai.turathaibackend.Entites.Comment;
import pi.turathai.turathaibackend.Services.CommentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // Nouveau POST avec CommentDTO
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentDTO dto) {
        Comment comment = commentService.createComment(
                dto.getContent(),
                dto.getImage(),
                dto.getUserId(),
                dto.getForumId()
        );
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public Optional<Comment> getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @GetMapping("/forum/{forumId}")
    public List<Comment> getCommentsByForum(@PathVariable Long forumId) {
        return commentService.getCommentsByForum(forumId);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}
