package com.example.fashionblog.repository;

import com.example.fashionblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostingRepository extends JpaRepository<Post,Long > {
    public List<Post> findByCategory(String category);
}
