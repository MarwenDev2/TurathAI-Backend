package pi.turathai.turathaibackend.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Entites.Comment;
import pi.turathai.turathaibackend.Services.CommentService;
import pi.turathai.turathaibackend.Services.ICommentService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins= "http://Localhost:4200")
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final ICommentService commentService;

    // Nouveau POST avec CommentDTO
    @PostMapping
    public Comment createComment(@RequestBody Comment dto) {
        return commentService.createComment(dto);
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
