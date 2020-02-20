package com.example.fashionblog.unitTests;

import com.example.fashionblog.model.Like;
import com.example.fashionblog.model.Post;
import com.example.fashionblog.repository.CommentingRepository;
import com.example.fashionblog.repository.LikingRepository;
import com.example.fashionblog.repository.PostingRepository;
import com.example.fashionblog.services.CommentService;
import com.example.fashionblog.services.LikeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {
    private static Post testPost1;
    private static Post testPost2;

    private static Like testLike1;
    private  static Like testLike2;
    private static Like testLike3;
    private  static  Like testLike4;


    @Mock
    private LikingRepository likeRepository;

    @Mock
    private PostingRepository postRepository;

    @InjectMocks
    private LikeService likeService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
        testPost1 = new Post("anakara", "good native nigerian attire");
        testPost2 = new Post("balenciaga", "original balenciaga");
        testPost1.setId(1L);
        testPost2.setId(2L);

        testLike1 = new Like("jide@gmail.com");
        testLike1.setPost(testPost1);
        testLike2 = new Like("njusticej@gmail.com");
        testLike1.setPost(testPost1);
        testLike3 = new Like("jyddyNwoks@yahoo.com");
        testLike3.setPost(testPost2);
        testLike4 = new Like("one@outlook.com");
        testLike4.setPost(testPost2);
    }

    @Test
    public void addLikeTest(){
        Mockito.when(postRepository.findById(testPost1.getId())).thenReturn(Optional.of(testPost1));
        Mockito.when(likeRepository.save(testLike1)).thenReturn(testLike1);
        assertThat(likeService.AddLike(testPost1.getId(), testLike1), is(testLike1));
        Mockito.verify(postRepository, Mockito.times(1)).findById(testPost1.getId());
        Mockito.verify(likeRepository, Mockito.times(1)).save(testLike1);
    }

    @Test
    public void getLikesForAPost(){
        Mockito.when(likeRepository.findByPostId(testPost1.getId())).thenReturn(Arrays.asList(testLike1,testLike2));
        assertThat(likeService.getLikesByPosts(testPost1.getId()).size(), is(2));
        Mockito.verify(likeRepository, Mockito.times(1)).findByPostId(testPost1.getId());
    }
}
