package com.example.fashionblog.unitTests;

import com.example.fashionblog.controlller.PostsController;
import com.example.fashionblog.model.Post;
import com.example.fashionblog.repository.PostingRepository;
import com.example.fashionblog.response.ApiResponseClass;
import com.example.fashionblog.services.PostServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;



@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PostControllerTest {

    private static Post testPost1;
    private static Post testPost2;

    @Mock
    private PostServiceImpl postService;

    @InjectMocks
    private PostsController postController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @BeforeAll
    public static void init(){
        testPost1 = new Post("anakara", "good native nigerian attire");
        testPost2 = new Post("balenciaga", "original balenciaga");
        testPost1.setId(1L);
        testPost1.setId(2L);
    }

    @Test
    public void postRequestTestToCreatePost(){
            Mockito.when(postService.createPost(testPost1)).thenReturn(testPost1);
            ResponseEntity<ApiResponseClass<Post>> postCreated = postController.createPost(testPost1);
            assertThat(postCreated.getStatusCode(), is(HttpStatus.CREATED));
            assertThat(Objects.requireNonNull(postCreated.getBody()).getData(), is(testPost1));
            assertThat(postCreated.getBody(), is(notNullValue()));
    }

    @Test
    public  void postRequestToGetAPost(){
        Mockito.when(postService.getAPost(testPost1.getId())).thenReturn(testPost1);
        ResponseEntity<ApiResponseClass<Post>> retrievedPost = postController.getATask(testPost1.getId());
        assertThat(retrievedPost.getStatusCode(), is(HttpStatus.OK));
        assertThat(Objects.requireNonNull(retrievedPost.getBody()).getData(), is(testPost1));
        assertThat(retrievedPost.getBody(), is(notNullValue()));
        assertThat(retrievedPost.getBody().getMessage(), is("Post Retrieved Successfully"));
    }

    @Test
    public  void getAllPostsRequestTest(){
        postService.createPost(testPost1);
        postService.createPost(testPost2);
        Mockito.when(postService.getAllPosts(0,2)).thenReturn(Arrays.asList(testPost1,testPost2));
        ResponseEntity<ApiResponseClass<List<Post>>> retrievedPosts = postController.getAllPosts(0,2);
        assertThat(retrievedPosts.getStatusCode(), is(HttpStatus.OK));
        assertThat(retrievedPosts.getBody(), is(notNullValue()));
        assertThat(retrievedPosts.getBody().getData().size(), is(2));
    }


    @Test
    public void getPostsByCategory(){
        Mockito.when(postService.findByCategory(testPost1.getCategory())).thenReturn((Collections.singletonList(testPost1)));
        ResponseEntity<ApiResponseClass<List<Post>>> retrievedPostsByCategory = postController.getPostByCategory(testPost1.getCategory());
        assertThat(retrievedPostsByCategory.getStatusCode(), is(HttpStatus.OK));
        assertThat(retrievedPostsByCategory.getBody(), is(notNullValue()));
        assertThat(retrievedPostsByCategory.getBody().getData().size(), is(1));
    }

    @Test
    public void deletePostTest(){
        Mockito.when(postService.deletePost(testPost1.getId())).thenReturn(testPost1.getId());
        ResponseEntity<ApiResponseClass<Post>> deletedPost = postController.deletePost(testPost1.getId());
        assertThat(deletedPost.getStatusCode(), is(HttpStatus.OK));
        assertThat(deletedPost.getBody(), is(notNullValue()));
        assertThat(deletedPost.getBody().getData(), is(nullValue()));
    }
}

