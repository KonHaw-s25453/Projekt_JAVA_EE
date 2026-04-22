package com.asms.controller;

import com.asms.dto.CommentDTO;
import com.asms.entity.Comment;
import com.asms.entity.User;
import com.asms.service.CommentService;
import com.asms.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Comment> create(@Valid @RequestBody CommentDTO dto, Authentication authentication) {
        User author = userService.findByUsername(authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(dto, author.getUserId()));
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAll() {
        return ResponseEntity.ok(commentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> update(@PathVariable Long id, @Valid @RequestBody CommentDTO dto) {
        return ResponseEntity.ok(commentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/public")
    public ResponseEntity<List<Comment>> getPublic() {
        return ResponseEntity.ok(commentService.getPublicComments());
    }

    @GetMapping("/entity/{type}/{entityId}")
    public ResponseEntity<List<Comment>> getByEntity(@PathVariable String type, @PathVariable Long entityId) {
        return ResponseEntity.ok(commentService.getByEntity(type, entityId));
    }
}
