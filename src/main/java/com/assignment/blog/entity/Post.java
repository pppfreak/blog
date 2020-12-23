package com.assignment.blog.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Post implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String body;
    private Date postedDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_comment_fk")
    private List<Comment> comments;

    @JsonIgnore
    @ManyToMany(mappedBy = "upvote")
    private  Set<User> upvote;

    @JsonIgnore
    @ManyToMany(mappedBy = "downVote")
    private  Set<User> downVote;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private Integer totalUpvote;

    private Integer totalDownVote;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Integer getTotalUpvote() {
        return totalUpvote;
    }

    public Integer getTotalDownVote() {
        return totalDownVote;
    }

    public void setTotalUpvote(Integer totalUpvote) {
        this.totalUpvote = totalUpvote;
    }

    public void setTotalDownVote(Integer totalDownVote) {
        this.totalDownVote = totalDownVote;
    }

    public Post() {
    }

    public Set<User> getUpvote() {
        return upvote;
    }

    public void setUpvote(User user) {
        this.upvote.add(user);
        user.getUpvote().add(this);
    }
    public void removeUserFromUpvote(User user){
        this.upvote.remove(user);
        user.getUpvote().remove(this);
    }
    public Set<User> getDownVote() {
        return downVote;
    }

    public void setDownVote(User user) {
        this.downVote.add(user);
        user.getDownVote().add(this);
    }

    public void removeUserFromDownVote(User user){
        this.downVote.remove(user);
        user.getDownVote().remove(this);
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
