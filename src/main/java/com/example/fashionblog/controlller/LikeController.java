package com.example.fashionblog.controlller;


import com.example.fashionblog.model.Comment;
import com.example.fashionblog.model.Like;
import com.example.fashionblog.response.ApiResponseClass;
import com.example.fashionblog.services.CommentService;
import com.example.fashionblog.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class LikeController {

    private LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {

        this.likeService = likeService;
    }

    String noErrorMessage = "No Error";
    String noDebugMessage = "No Error to debug";

    @PostMapping
    @RequestMapping("/{postId}/like")
    public ResponseEntity<ApiResponseClass<Like>> addLike(@PathVariable(value = "postId") Long postId,
                                                                   @Valid @RequestBody Like like){
        Like createdLike = likeService.AddLike(postId, like);
        ApiResponseClass<Like> responseForCreatedPost = new ApiResponseClass<>(HttpStatus.CREATED);
        responseForCreatedPost.setMessage("like Made Successfully");
        responseForCreatedPost.setData(createdLike);
        responseForCreatedPost.setError(noErrorMessage);
        responseForCreatedPost.setDebugMessage(noDebugMessage);
        return  new ResponseEntity<>(responseForCreatedPost, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{postId}/like", method = RequestMethod.GET)
    public ResponseEntity<ApiResponseClass<List<Like>>> geLikesForAPost (@PathVariable(value = "postId") Long postId){
        List<Like> commentsGottenForAPost = likeService.getLikesByPosts(postId);
        ApiResponseClass<List<Like>> responseForCommentsFoundForAPost = new ApiResponseClass<>(HttpStatus.OK);
        responseForCommentsFoundForAPost.setMessage(commentsGottenForAPost.size() + " Likes Retrieved for post with id: " + postId );
        responseForCommentsFoundForAPost.setData(commentsGottenForAPost);
        return new ResponseEntity<>(responseForCommentsFoundForAPost, HttpStatus.OK);
    }
}
