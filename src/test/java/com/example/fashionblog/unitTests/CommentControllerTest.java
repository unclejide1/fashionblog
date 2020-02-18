package com.example.fashionblog.unitTests;

import com.example.fashionblog.controlller.CommentController;
import com.example.fashionblog.controlller.PostsController;
import com.example.fashionblog.model.Comment;
import com.example.fashionblog.model.Post;
import com.example.fashionblog.response.ApiResponseClass;
import com.example.fashionblog.services.CommentService;
import com.example.fashionblog.services.PostServiceImpl;
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

import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    private static Post testPost1;
    private static Post testPost2;

    private static Comment testComment1;
    private static Comment testComment2;
    private static Comment testComment3;
    private static Comment testComment4;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init(){
        testPost1 = new Post("anakara", "good native nigerian attire");
        testPost2 = new Post("balenciaga", "original balenciaga");
        testPost1.setId(1L);
        testPost2.setId(2L);

        testComment1 = new Comment("njusticej@gmail.com", "my comment", "nice shoes");
        testComment1.setId(1L);
        testComment1.setPost(testPost1);

        testComment2= new Comment("njus@gmail.com","lovely ankara", "nice anakara");
        testComment2.setId(2L);
        testComment2.setPost(testPost1);

        testComment3 = new Comment("njustice@gmail.com", "balenciaga", "is your balenciaga original?");
        testComment3.setId(3L);
        testComment3.setPost(testPost2);

        testComment4 = new Comment("njustic@gmail.com", "balenciaga", "is your balenciaga fake?");
        testComment4.setId(3L);
        testComment4.setPost(testPost2);
    }


    @Test
    public void postRequestToCreateAComment(){
        Mockito.when(commentService.createComment(testPost1.getId(), testComment1)).thenReturn(testComment1);
        ResponseEntity<ApiResponseClass<Comment>> commentMade = commentController.createComment(testPost1.getId(),testComment1);
        assertThat(commentMade.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(Objects.requireNonNull(commentMade.getBody()).getData(), is(testComment1));
        assertThat(Objects.requireNonNull(commentMade.getBody()).getData().getPost(), is(testPost1));
    }

    @Test
    public void deleteRequestToDeleteComment(){
        Mockito.when(commentService.deleteComment(testPost1.getId(), testComment1.getId())).thenReturn(testComment1.getId());
        ResponseEntity<ApiResponseClass<Comment>> deletedCommentResponse = commentController.deleteComment(testPost1.getId(), testComment1.getId());
        assertThat(deletedCommentResponse.getStatusCode(), is(HttpStatus.OK));
        assertThat(Objects.requireNonNull(deletedCommentResponse.getBody()).getMessage(), is("Comment with id " + testComment1.getId() + " has been deleted Successfully"));
    }

    @Test
    public void getCommentsForPost(){
        Mockito.when(commentService.getCommentsByPosts(testPost1.getId())).thenReturn(Arrays.asList(testComment1, testComment2));
        ResponseEntity<ApiResponseClass<List<Comment>>> commentsForAPost = commentController.getCommentsForAPost(testPost1.getId());
        assertThat(commentsForAPost.getBody(), is(notNullValue()));
        assertThat(commentsForAPost.getBody().getData().size(), is(2));
    }
}
