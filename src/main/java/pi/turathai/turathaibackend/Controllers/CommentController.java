package pi.turathai.turathaibackend.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.DTO.CommentDTO;
import pi.turathai.turathaibackend.Entites.Comment;
import pi.turathai.turathaibackend.Services.CommentService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins= "http://localhost:4200")
@RestController
@RequestMapping("/api/comments/")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // La route ici attend maintenant un forumId dans l'URL
    @PostMapping("/forums/{forumId}") // Correction de la route POST ici
    public ResponseEntity<Comment> createComment(@PathVariable Long forumId, @RequestBody CommentDTO dto) {
        // Utilisation du forumId récupéré de l'URL
        Comment comment = commentService.createComment(
                dto.getContent(),
                dto.getImage(),
                dto.getUserId(),
                forumId  // Passer le forumId récupéré de l'URL ici
        );
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Optional<Comment> getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @GetMapping("/forum/{forumId}")
    public List<Comment> getCommentsByForum(@PathVariable Long forumId) {
        return commentService.getCommentsByForum(forumId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @Valid @RequestBody CommentDTO dto) {
        Comment updated = commentService.updateComment(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}