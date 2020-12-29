package com.assignment.blog.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class
User implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    private String userName;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Post> postList;

    @JsonIgnore
    @ManyToMany(cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "upvote",joinColumns = @JoinColumn(name = "userId"),inverseJoinColumns =
    @JoinColumn(name = "postId"))
    private Set<Post> upvote;

    @JsonIgnore
    @ManyToMany(cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "downVote",joinColumns = @JoinColumn(name = "userId"),inverseJoinColumns =
    @JoinColumn(name = "postId"))
    private Set<Post> downVote;


    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Post> getUpvote() {
        return upvote;
    }

    public void setUpvote(Set<Post> upvote) {
        this.upvote = upvote;
    }

    public Set<Post> getDownVote() {
        return downVote;
    }

    public void setDownVote(Set<Post> downVote) {
        this.downVote = downVote;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
