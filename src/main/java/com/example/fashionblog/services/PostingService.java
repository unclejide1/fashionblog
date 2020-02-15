package com.example.fashionblog.services;

import com.example.fashionblog.model.Post;

import java.util.List;

public interface PostingService {
    Post createPost(Post post);
    Post getAPost(Long id);
    List<Post> getAllPosts();
    List<Post> findByCategory(String category);
    Post updatePost(Post postToUpdate, Long id);
    Long deletePost(Long id);
}
