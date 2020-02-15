package com.example.fashionblog.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@Table(name = "posts")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Post {

//    private static final long serialVersionUID = -1798070786993154676L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotBlank

    @Size(min = 4, max = 255, message = "Minimum category should be of length: 4 characters")
//    @JsonDeserialize(using = CustomDeserializer.class)
    private String category;

    @NotNull
    @NotBlank
    private  String description;


    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Timestamp createAt;


    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Timestamp updatedAt;

//    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
//    @LazyCollection(LazyCollectionOption.FALSE)
//    private  List<Comments> comments;
//
//    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
//    @LazyCollection(LazyCollectionOption.FALSE)
//    private  List<Likes> likes;

    public Post() {
    }

//    public static long getSerialVersionUID() {
//        return serialVersionUID;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

//    public List<Comments> getComments() {
//        return comments;
//    }
//
//    public void setComments(List<Comments> comments) {
//        this.comments = comments;
//    }
//
//    public List<Likes> getLikes() {
//        return likes;
//    }
//
//    public void setLikes(List<Likes> likes) {
//        this.likes = likes;
//    }
}
