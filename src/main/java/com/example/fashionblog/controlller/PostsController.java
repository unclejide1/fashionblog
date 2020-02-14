package com.example.fashionblog.controlller;

import com.example.fashionblog.model.Posts;
import com.example.fashionblog.repository.PostingRepository;
import com.example.fashionblog.response.ApiResponseClass;
import com.example.fashionblog.services.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostsController {
    private PostServiceImpl postService;

    @Autowired
    public PostsController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseClass<Posts>> createTask(@Valid  @RequestBody Posts post){
        Posts P = postService.createPost(post);
        ApiResponseClass<Posts> ath = new ApiResponseClass<>(HttpStatus.CREATED);
        ath.setMessage("Successfully Created");
        ath.setData(P);
        return  new ResponseEntity<>(ath, HttpStatus.CREATED);
    }
}
