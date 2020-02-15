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
    public Like AddLike(Long postId, Like like) {
        Post postToBeLiked = postingRepository.findById(postId).orElse(null);
        if(postToBeLiked == null){
            throw new CustomException("Post not found for parameters {id=" + postId + "} to be commented on", HttpStatus.BAD_REQUEST);
        }
        int initialLikes = like.getLikesCount();
        like.setLikesCount(++initialLikes);
        like.setPost(postToBeLiked);
        return likingRepository.save(like);
    }


    @Override
    public List<Like> getLikesByPosts(Long postId) {
        List<Like> commentsForAPost = likingRepository.findByPostId(postId);
        if(commentsForAPost.isEmpty()){
            throw new CustomException("No comments for posts with id: " + postId, HttpStatus.BAD_REQUEST);
        }
        return commentsForAPost;
    }

//    @Override
//    public Like unLike(Long postId, Long likedId, Like likeToUnlike) {
//        Post postToBeToUnLiked = postingRepository.findById(postId).orElse(null);
//        if(postToBeToUnLiked == null){
//            throw new CustomException("Post not found for parameters {postId=" + postId + "} with any comment to be unLiked", HttpStatus.BAD_REQUEST);
//        }
//        Like oldLike = likingRepository.findById(likedId).orElse(null);
//        if(oldLike == null || !(oldLike.getEmail().equalsIgnoreCase(likeToUnlike.getEmail()))){
//            throw new CustomException("Like not found for parameters  {postId=" + postId + "} & {likeId= " + likedId + "} to be unLiked or like  was not made by user with " + likeToUnlike.getEmail(), HttpStatus.BAD_REQUEST);
//        }
//        if(oldLike.getLikesCount() <= 0){
//
//        }
//        return null;
//    }
}
