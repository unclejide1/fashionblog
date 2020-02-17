package com.example.fashionblog.model;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "likes")
public class Like {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;



    @Column(nullable = false)
    @Email
    private  String email;


    @ManyToOne(fetch = FetchType.EAGER,
            optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    public Like() {
    }

    public Like(String email){
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public int getLikesCount() {
//        return likesCount;
//    }
//
//    public void setLikesCount(int likesCount) {
//        this.likesCount = likesCount;
//    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
