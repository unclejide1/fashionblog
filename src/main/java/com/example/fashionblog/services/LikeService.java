package com.example.fashionblog.services;

import com.example.fashionblog.exception.CustomException;
import com.example.fashionblog.model.Comment;
import com.example.fashionblog.model.Like;
import com.example.fashionblog.model.Post;
import com.example.fashionblog.repository.CommentingRepository;
import com.example.fashionblog.repository.LikingRepository;
import com.example.fashionblog.repository.PostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LikeService implements LikingService {

    private PostingRepository postingRepository;

    private LikingRepository likingRepository;

    @Autowired
    public LikeService(PostingRepository postingRepository, LikingRepository likingRepository) {
        this.postingRepository = postingRepository;
        this.likingRepository= likingRepository;
    }


    @Override
    public Like AddLike(Long postId, Like like) throws CustomException {
        Post postToBeLiked = postingRepository.findById(postId).orElse(null);
        if(postToBeLiked == null){
            throw new CustomException("Post not found for parameters {id=" + postId + "} to be liked", HttpStatus.BAD_REQUEST);
        }
        like.setPost(postToBeLiked);
        return likingRepository.save(like);
    }


    @Override
    public List<Like> getLikesByPosts(Long postId) throws CustomException {
        List<Like> commentsForAPost = likingRepository.findByPostId(postId);
        if(commentsForAPost.isEmpty()){
            throw new CustomException("No likes for posts with id: " + postId, HttpStatus.BAD_REQUEST);
        }
        return commentsForAPost;
    }

}
