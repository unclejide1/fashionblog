package com.example.fashionblog.services;

import com.example.fashionblog.model.Like;

import java.util.List;

public interface LikingService {
    Like AddLike(Long postId, Like like);
//    Like unLike(Long postId, Long likeId, Like likeToUnlike);
List<Like> getLikesByPosts(Long postId);
}
