package com.example.fashionblog.controlller;

import com.example.fashionblog.model.Post;
import com.example.fashionblog.response.ApiResponseClass;
import com.example.fashionblog.services.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostsController {
    private PostServiceImpl postService;

    @Autowired
    public PostsController(PostServiceImpl postService) {
        this.postService = postService;
    }

    String noErrorMessage = "No Error";
    String noDebugMessage = "No Error to debug";

    @PostMapping
    public ResponseEntity<ApiResponseClass<Post>> createPost(@Valid  @RequestBody Post post){
        Post createdPost = postService.createPost(post);
        ApiResponseClass<Post> responseForCreatedPost = new ApiResponseClass<>(HttpStatus.CREATED);
        responseForCreatedPost.setMessage("Successfully Created");
        responseForCreatedPost.setData(createdPost);
        responseForCreatedPost.setError(noErrorMessage);
        responseForCreatedPost.setDebugMessage(noDebugMessage);
        return  new ResponseEntity<>(responseForCreatedPost, HttpStatus.CREATED);
    }

    @GetMapping
    @RequestMapping("{id}")
    public ResponseEntity<ApiResponseClass<Post>> getATask(@PathVariable Long id){
            Post foundPost = postService.getAPost(id);

                ApiResponseClass<Post> responseForFoundPost = new ApiResponseClass<>(HttpStatus.OK);
                responseForFoundPost.setMessage("Post Retrieved Successfully");
                responseForFoundPost.setData(foundPost);
                responseForFoundPost.setError(noErrorMessage);
                responseForFoundPost.setDebugMessage(noDebugMessage);
                return  new ResponseEntity<>(responseForFoundPost, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<ApiResponseClass<List<Post>>> getAllPosts(@RequestParam(value = "page", defaultValue = "0") Integer pageNo, @RequestParam(value = "limit", defaultValue = "10") Integer noOfContent){
        List<Post> allPosts = postService.getAllPosts(pageNo,noOfContent);
        return getApiResponseClassResponseEntity(allPosts);
    }


    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public ResponseEntity<ApiResponseClass<List<Post>>> getPostByCategory(@RequestParam String category){
        List<Post> foundPostByCategory = postService.findByCategory(category.toLowerCase());
        return getApiResponseClassResponseEntity(foundPostByCategory);
    }

    private ResponseEntity<ApiResponseClass<List<Post>>> getApiResponseClassResponseEntity(List<Post> foundPostByCategory) {
        ApiResponseClass<List<Post>> responseForFoundByCategory = new ApiResponseClass<>(HttpStatus.OK);
        responseForFoundByCategory.setMessage("All Posts Retrieved Successfully");
        responseForFoundByCategory.setData(foundPostByCategory);
        responseForFoundByCategory.setError(noErrorMessage);
        responseForFoundByCategory.setDebugMessage(noDebugMessage);
        return new ResponseEntity<>(responseForFoundByCategory, HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<ApiResponseClass<Post>> updatePost(@Valid @RequestBody Post post, @PathVariable Long id){
        Post updatedPost = postService.updatePost(post, id);
        ApiResponseClass<Post> responseForUpdatedPost = new ApiResponseClass<>(HttpStatus.OK);
        responseForUpdatedPost.setMessage("post with" + id + " has been updated");
        responseForUpdatedPost.setData(updatedPost);
        responseForUpdatedPost.setError(noErrorMessage);
        responseForUpdatedPost.setDebugMessage(noDebugMessage);
        return  new ResponseEntity<>(responseForUpdatedPost, HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiResponseClass<Post>> deletePost(@PathVariable(name = "id") Long id){
        Long deletedId = postService.deletePost(id);
        ApiResponseClass<Post> deletedResponse = new ApiResponseClass<>(HttpStatus.OK);
        deletedResponse.setMessage("post with id: " + id + " has been deleted");
        deletedResponse.setError(noErrorMessage);
        deletedResponse.setDebugMessage(noDebugMessage);
        return new ResponseEntity<>(deletedResponse, HttpStatus.OK);
    }

}
