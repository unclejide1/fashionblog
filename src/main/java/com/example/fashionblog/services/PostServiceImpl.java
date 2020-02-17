package com.example.fashionblog.services;

//import com.example.fashionblog.errorHandler.EntityNotFoundException;
import com.example.fashionblog.exception.CustomException;
import com.example.fashionblog.model.Post;
import com.example.fashionblog.repository.PostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
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
    public Post getAPost(Long id) throws CustomException {
        Post foundPost = postingRepository.findById(id).orElse(null);
        if(foundPost == null){
            throw new CustomException("Post not found for parameters {id=" + id + "}", HttpStatus.BAD_REQUEST);
        }
        return foundPost;
    }

    @Override
    public List<Post> getAllPosts(Integer pageNo, Integer noOfContent) throws CustomException {
        Pageable pagination = PageRequest.of(pageNo,noOfContent, Sort.by(Sort.Direction.ASC, "id"));

        Slice<Post> allPosts = postingRepository.findAll(pagination);
        if(!allPosts.hasContent()) {
            throw new CustomException("There are no Posts available", HttpStatus.BAD_REQUEST);
        }
        List<Post> retrievedPosts = allPosts.getContent();
        System.out.println(retrievedPosts);
        return retrievedPosts;

    }

    @Override
    public List<Post> findByCategory(String category) throws CustomException {
        List<Post> postFoundByCategory = postingRepository.findByCategory(category.toLowerCase());
        System.out.println(postFoundByCategory);
        if(postFoundByCategory.isEmpty()){
            throw new CustomException("There are no available posts of category: " + category, HttpStatus.BAD_REQUEST);
        }
        return postFoundByCategory;
    }

    @Override
    public Post updatePost(Post postToUpdate, Long id) throws  CustomException{
        Post oldPost = postingRepository.findById(id).orElse(null);
        if(oldPost == null){
            throw new CustomException("Post not found for parameters {id=" + id + "} " + " to be updated", HttpStatus.BAD_REQUEST);
        }
        oldPost.setCategory(postToUpdate.getCategory());
        oldPost.setDescription(postToUpdate.getDescription());
        return postingRepository.save(oldPost);
    }

    @Override
    public Long deletePost(Long id) throws CustomException{
        if(!postingRepository.existsById(id)){
            throw new CustomException("Post not found for parameters {id=" + id + "} " + " to be deleted", HttpStatus.BAD_REQUEST);
        }
        postingRepository.deleteById(id);
        return id;
    }
}
