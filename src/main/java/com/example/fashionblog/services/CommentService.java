package com.example.fashionblog.services;


import com.example.fashionblog.exception.CustomException;
import com.example.fashionblog.model.Comment;
import com.example.fashionblog.model.Post;
import com.example.fashionblog.repository.CommentingRepository;
import com.example.fashionblog.repository.PostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements CommentingService {


    private PostingRepository postingRepository;

    private CommentingRepository commentingRepository;

    @Autowired
    public CommentService(PostingRepository postingRepository, CommentingRepository commentingRepository) {
        this.postingRepository = postingRepository;
        this.commentingRepository = commentingRepository;
    }

    @Override
    public Comment createComment(Long postId, Comment comment) throws CustomException {
        Post postToBeCommentedOn = postingRepository.findById(postId).orElse(null);
        if(postToBeCommentedOn == null){
            throw new CustomException("Post not found for parameters {id=" + postId + "} to be commented on", HttpStatus.BAD_REQUEST);
        }
        comment.setPost(postToBeCommentedOn);
        return commentingRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long postId, Long commentId, Comment commentToUpdate) throws CustomException {
        Post postToUpdateComment = postingRepository.findById(postId).orElse(null);
        if(postToUpdateComment == null){
            throw new CustomException("Post not found for parameters {postId=" + postId + "} with any comment to be updated", HttpStatus.BAD_REQUEST);
        }
        Comment oldComment = commentingRepository.findById(commentId).orElse(null);
        if(oldComment == null || !(oldComment.getEmail().equalsIgnoreCase(commentToUpdate.getEmail()))){
            throw new CustomException("Comment not found for parameters  {postId=" + postId + "} {id=" + commentId + "} with any comment to be updated comment was not made by user with " + commentToUpdate.getEmail(), HttpStatus.BAD_REQUEST);
        }
        oldComment.setTitle(commentToUpdate.getTitle());
        oldComment.setComments(commentToUpdate.getComments());
        return commentingRepository.save(oldComment);
    }

    @Override
    public Long deleteComment(Long postId, Long commentId) throws CustomException {
        Comment checkIfCommentExist = commentingRepository.findByIdAndPostId(commentId, postId).orElse(null);

        if(checkIfCommentExist == null){
            throw new CustomException("No comment or post with passed id ", HttpStatus.BAD_REQUEST);
        }
        commentingRepository.delete(checkIfCommentExist);
        return commentId;
    }

    @Override
    public List<Comment> getCommentsByPosts(Long postId) throws CustomException {
        List<Comment> commentsForAPost = commentingRepository.findByPostId(postId);
        if(commentsForAPost.isEmpty()){
            throw new CustomException("No comments for posts with id: " + postId, HttpStatus.OK);
        }
        return commentsForAPost;
    }
}
