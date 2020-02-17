package com.example.fashionblog.unitTests;

import com.example.fashionblog.controlller.CommentController;
import com.example.fashionblog.controlller.LikeController;
import com.example.fashionblog.model.Comment;
import com.example.fashionblog.model.Like;
import com.example.fashionblog.model.Post;
import com.example.fashionblog.response.ApiResponseClass;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class LikeControllerTest {
    private static Post testPost1;
    private static Post testPost2;

    private static Like testLike1;
    private  static Like testLike2;
    private static Like testLike3;
    private  static  Like testLike4;

    @Mock
    private LikeService likeService;

    @InjectMocks
    private LikeController likeController;

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
    public void postRequestToAddLike(){
        Mockito.when(likeService.AddLike(testPost1.getId(), testLike1)).thenReturn(testLike1);
        ResponseEntity<ApiResponseClass<Like>> likeMade = likeController.addLike(testPost1.getId(), testLike1);
        assertThat(likeMade.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(Objects.requireNonNull(likeMade.getBody()).getData(), is(testLike1));
        assertThat(Objects.requireNonNull(likeMade.getBody()).getData().getPost(), is(testPost1));
    }

    @Test
    public void getLikesForAPost(){
        Mockito.when(likeService.getLikesByPosts(testPost1.getId())).thenReturn(Arrays.asList(testLike1, testLike2));
        ResponseEntity<ApiResponseClass<List<Like>>> likesForAPost = likeController.geLikesForAPost(testPost1.getId());
        assertThat(likesForAPost.getBody(), is(notNullValue()));
        assertThat(likesForAPost.getBody().getData().size(), is(2));
    }
}
