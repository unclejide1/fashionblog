package com.example.fashionblog.unitTests;


import com.example.fashionblog.model.Comment;
import com.example.fashionblog.model.Post;
import com.example.fashionblog.repository.CommentingRepository;
import com.example.fashionblog.repository.PostingRepository;
import com.example.fashionblog.services.CommentService;
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

import java.util.Optional;

import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CommentServiceTest {


    private static Post testPost1;
    private static Post testPost2;

    private static Comment testComment1;
    private static Comment testComment2;
    private static Comment testComment3;
    private static Comment testComment4;

    @Mock
    private CommentingRepository commentRepository;

    @Mock
    private PostingRepository postRepository;

    @InjectMocks
    private CommentService commentService;


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
    public void createCommentTest(){
        Mockito.when(postRepository.findById(testPost1.getId())).thenReturn(Optional.of(testPost1));
        Mockito.when(commentRepository.save(testComment1)).thenReturn(testComment1);

        assertThat(commentService.createComment(testPost1.getId(),testComment1), is(testComment1));
        Mockito.verify(postRepository, Mockito.times(1)).findById(testPost1.getId());
        Mockito.verify(commentRepository, Mockito.times(1)).save(testComment1);
    }

    @Test
    public void getCommentByPostTest(){
        Mockito.when(commentRepository.findByPostId(testPost1.getId())).thenReturn(Arrays.asList(testComment1,testComment2));
        assertThat(commentService.getCommentsByPosts(testPost1.getId()).size(), is(2));
        Mockito.verify(commentRepository, Mockito.times(1)).findByPostId(testPost1.getId());
    }

    @Test
    public void deleteCommentTest(){
        Mockito.when(commentRepository.findByIdAndPostId(testComment1.getId(), testPost1.getId())).thenReturn(Optional.ofNullable(testComment1));
        assertThat(commentService.deleteComment(testPost1.getId(), testComment1.getId()), is(testComment1.getId()));
        Mockito.verify(commentRepository, Mockito.times(1)).findByIdAndPostId(testComment1.getId(), testPost1.getId());
    }

//    @Test
//    public void

}
