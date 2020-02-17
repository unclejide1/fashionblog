package com.example.fashionblog.repository;

import com.example.fashionblog.model.Comment;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentingRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long PostId);
    Optional<Comment> findByIdAndPostId(Long id, Long PostId);
}
