package com.example.fashionblog.services;

import com.example.fashionblog.model.Comment;

import java.util.List;

public interface CommentingService {
    Comment createComment(Long postId, Comment comment);
    Comment updateComment(Long postId, Long commentId, Comment commentToUpdate);
    Long deleteComment(Long postId, Long commentId);
    List<Comment> getCommentsByPosts(Long postId);
}
