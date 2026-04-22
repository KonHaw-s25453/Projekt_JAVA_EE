package com.asms.service;

import com.asms.dto.CommentDTO;
import com.asms.entity.Comment;
import com.asms.entity.User;
import com.asms.exception.ResourceNotFoundException;
import com.asms.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment create(CommentDTO dto, Long authorId) {
        User author = new User();
        author.setUserId(authorId);
        Comment comment = Comment.builder()
                .entityType(dto.getEntityType())
                .entityId(dto.getEntityId())
                .author(author)
                .content(dto.getContent())
                .visibility(dto.getVisibility() != null ? dto.getVisibility() : "PUBLIC")
                .build();
        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public Comment getById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
    }

    @Transactional(readOnly = true)
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Comment> getByEntity(String entityType, Long entityId) {
        return commentRepository.findByEntityTypeAndEntityId(entityType, entityId);
    }

    @Transactional(readOnly = true)
    public List<Comment> getPublicComments() {
        return commentRepository.findPublicComments();
    }

    public Comment update(Long id, CommentDTO dto) {
        Comment comment = getById(id);
        comment.setContent(dto.getContent());
        comment.setVisibility(dto.getVisibility());
        return commentRepository.save(comment);
    }

    public void delete(Long id) {
        Comment comment = getById(id);
        commentRepository.delete(comment);
    }
}
