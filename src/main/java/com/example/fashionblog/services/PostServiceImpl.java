package com.example.fashionblog.services;

//import com.example.fashionblog.errorHandler.EntityNotFoundException;
import com.example.fashionblog.exception.CustomException;
import com.example.fashionblog.model.Post;
import com.example.fashionblog.repository.PostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PostServiceImpl implements PostingService {

    private PostingRepository postingRepository;

    @Autowired
    public PostServiceImpl(PostingRepository postingRepository) {
        this.postingRepository = postingRepository;
    }

    @Override
    public Post createPost(Post post) {
        return this.postingRepository.save(post);
    }

    @Override
    public Post getAPost(Long id) {
        Post foundPost = postingRepository.findById(id).orElse(null);
        if(foundPost == null){
            throw new CustomException("Post not found for parameters {id=" + id + "}", HttpStatus.BAD_REQUEST);
        }
        return foundPost;
    }

    @Override
    public List<Post> getAllPosts() throws CustomException {
        List<Post> allPosts = postingRepository.findAll();
        if(allPosts.isEmpty()){
            throw new CustomException("There are no Posts available", HttpStatus.BAD_REQUEST);
        }
        return allPosts;
    }

    @Override
    public List<Post> findByCategory(String category) {

        List<Post> postFoundByCategory = postingRepository.findByCategory(category);
        if(postFoundByCategory.isEmpty()){
            throw new CustomException("There are no available posts of category: " + category, HttpStatus.BAD_REQUEST);
        }
        return postFoundByCategory;
    }

    @Override
    public Post updatePost(Post postToUpdate, Long id){
        Post oldPost = postingRepository.findById(id).orElse(null);
        if(oldPost == null){
            throw new CustomException("Post not found for parameters {id=" + id + "} " + " to be updated", HttpStatus.BAD_REQUEST);
        }
        oldPost.setCategory(postToUpdate.getCategory());
        oldPost.setDescription(postToUpdate.getDescription());
        return postingRepository.save(oldPost);
    }

    @Override
    public Long deletePost(Long id){
        if(!postingRepository.existsById(id)){
            throw new CustomException("Post not found for parameters {id=" + id + "} " + " to be deleted", HttpStatus.BAD_REQUEST);
        }
        postingRepository.deleteById(id);
        return id;
    }
}
