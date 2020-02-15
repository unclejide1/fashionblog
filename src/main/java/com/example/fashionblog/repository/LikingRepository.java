package com.example.fashionblog.repository;

import com.example.fashionblog.model.Comment;
import com.example.fashionblog.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikingRepository extends JpaRepository<Like, Long> {
    List<Like> findByPostId(Long PostId);
}
