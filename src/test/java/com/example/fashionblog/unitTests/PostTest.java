package com.example.fashionblog.unitTests;


import com.example.fashionblog.model.Post;
import com.example.fashionblog.repository.PostingRepository;
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


import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;



@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PostTest {

    private static Post testPost1;
    private static Post testPost2;

    @Mock
    private PostingRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

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
    public void createPostTest(){
        Mockito.when(postRepository.save(testPost1)).thenReturn(testPost1);
        assertThat(postService.createPost(testPost1), is(testPost1));
        Mockito.verify(postRepository, Mockito.times(1)).save(testPost1);
    }

    @Test
    public void getAPost(){
        Mockito.when(postRepository.findById(testPost1.getId())).thenReturn(Optional.of(testPost1));
        assertThat(postService.getAPost(testPost1.getId()), is(testPost1));
        assertThat(postService.getAPost(testPost1.getId()).getCategory(), is(testPost1.getCategory()));
        Mockito.verify(postRepository, Mockito.times(2)).findById(testPost1.getId());
    }


    @Test
    public  void getAPostByCategory(){
        Mockito.when(postRepository.findByCategory(testPost1.getCategory())).thenReturn(Collections.singletonList(testPost1));
        assertThat(postService.findByCategory(testPost1.getCategory()), is(Collections.singletonList(testPost1)));
        Mockito.verify(postRepository, Mockito.times(1)).findByCategory(testPost1.getCategory());
    }

    @Test
    public void  deleteAPost(){
        Mockito.when(postRepository.existsById(testPost1.getId())).thenReturn(true);
        assertThat(postService.deletePost(testPost1.getId()), is(testPost1.getId()));
        Mockito.verify(postRepository, Mockito.times(1)).existsById(testPost1.getId());

    }

}
