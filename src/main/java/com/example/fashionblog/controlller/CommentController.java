package com.example.fashionblog.controlller;

import com.example.fashionblog.model.Comment;
import com.example.fashionblog.model.Post;
import com.example.fashionblog.response.ApiResponseClass;
import com.example.fashionblog.services.CommentService;
import com.example.fashionblog.services.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {

        this.commentService = commentService;
    }

    String noErrorMessage = "No Error";
    String noDebugMessage = "No Error to debug";

    @PostMapping
    @RequestMapping("/{postId}/comment")
    public ResponseEntity<ApiResponseClass<Comment>> createComment(@PathVariable(value = "postId") Long postId,
                                                                   @Valid @RequestBody Comment comment){
        Comment createdComment = commentService.createComment(postId, comment);
        ApiResponseClass<Comment> responseForCreatedPost = new ApiResponseClass<>(HttpStatus.CREATED);
        responseForCreatedPost.setMessage("Comment Made Successfully");
        responseForCreatedPost.setData(createdComment);
        responseForCreatedPost.setError(noErrorMessage);
        responseForCreatedPost.setDebugMessage(noDebugMessage);
        return  new ResponseEntity<>(responseForCreatedPost, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{postId}/comment/{commentId}", method = RequestMethod.PUT)
    public ResponseEntity<ApiResponseClass<Comment>> updateComment(@PathVariable(value = "postId") Long postId,@PathVariable(value = "commentId") Long commentId,
                                                                   @Valid @RequestBody Comment comment){
        Comment updatedComment = commentService.updateComment(postId, commentId,comment);
        ApiResponseClass<Comment> responseForUpdatedPost = new ApiResponseClass<>(HttpStatus.OK);
        responseForUpdatedPost.setMessage("Comment has with id " + updatedComment.getId() + " has been updated successfully by " + updatedComment.getEmail());
        responseForUpdatedPost.setData(updatedComment);
        responseForUpdatedPost.setError(noErrorMessage);
        responseForUpdatedPost.setDebugMessage(noDebugMessage);
        return new ResponseEntity<>(responseForUpdatedPost, HttpStatus.OK);
    }

    @RequestMapping(path = "/{postId}/comment/{commentId}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiResponseClass<Comment>> deleteComment(@PathVariable(value = "postId") Long postId,@PathVariable(value = "commentId") Long commentId){
        Long deletedCommentId = commentService.deleteComment(postId, commentId);
        ApiResponseClass<Comment> responseForDeletedPost = new ApiResponseClass<>(HttpStatus.OK);
        responseForDeletedPost.setMessage("Comment with id " + deletedCommentId + " has been deleted Successfully");
        responseForDeletedPost.setError(noErrorMessage);
        responseForDeletedPost.setDebugMessage(noDebugMessage);
        return  new ResponseEntity<>(responseForDeletedPost, HttpStatus.OK);
    }

    @RequestMapping(path = "/{postId}/comment", method = RequestMethod.GET)
    public ResponseEntity<ApiResponseClass<List<Comment>>> getCommentsForAPost (@PathVariable(value = "postId") Long postId){
        List<Comment> commentsGottenForAPost = commentService.getCommentsByPosts(postId);
        ApiResponseClass<List<Comment>> responseForCommentsFoundForAPost = new ApiResponseClass<>(HttpStatus.OK);
        responseForCommentsFoundForAPost.setMessage("Comments Retrieved for post with id: " + postId );
        responseForCommentsFoundForAPost.setData(commentsGottenForAPost);
        return new ResponseEntity<>(responseForCommentsFoundForAPost, HttpStatus.OK);
    }

}
