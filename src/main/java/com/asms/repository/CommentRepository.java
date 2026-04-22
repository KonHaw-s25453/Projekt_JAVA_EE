package com.asms.repository;

import com.asms.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByEntityTypeAndEntityId(String entityType, Long entityId);

    @Query("SELECT c FROM Comment c WHERE c.visibility = 'PUBLIC'")
    List<Comment> findPublicComments();

    @Query("SELECT c FROM Comment c WHERE c.author.userId = :authorId")
    List<Comment> findByAuthor(@Param("authorId") Long authorId);
}
