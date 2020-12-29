package com.assignment.blog.controller;

import com.assignment.blog.entity.Comment;
import com.assignment.blog.entity.User;
import com.assignment.blog.model.PostForm;
import com.assignment.blog.model.UserLogInForm;
import com.assignment.blog.response.PostResponse;
import com.assignment.blog.service.PostService;
import com.assignment.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    private final PostService postService;
    @Autowired
    public UserController(UserService userService , PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }
    @PostMapping
    public User createUser(@RequestBody User user) throws Exception {
        return userService.signUpUser(user);
    }

    // TODO-- 1. User can create new posts
    @PostMapping("/{userId}")
    public PostResponse createPost(@RequestBody PostForm post, @PathVariable Integer userId){
        return postService.savePost(post ,userId);
    }

    // TODO-- 2. User can view posts of other users
    @GetMapping("/viewOtherPosts")
    public List<PostResponse> viewOthersPosts(){
        return userService.viewOthersPosts();
    }

    // TODO-- 3.  User can comment  on posts of other posts
    @PostMapping("comment/{postId}")
    public void addCommentOnOtherPost(@RequestBody Comment comment, @PathVariable Integer postId){
        userService.addCommentOnOtherPost(comment,postId);
    }

    // TODO-- 4. User can upvote / downvote posts of other users.
    @PostMapping("upvote/{userId}/{title}")
    public void upvoteParticularPost(@PathVariable Integer userId,@PathVariable String title){
        userService.upvoteParticularPost(userId,title);
    }
    @PostMapping("downVote/{userId}/{title}")
    public void downVoteParticularPost(@PathVariable Integer userId,@PathVariable String title){
        userService.downVoteParticularPost(userId,title);
    }


    // TODO-- 5. User can view comments of a particular post
    // TODO-- 6. User can see upvote and downvote counts of a particular post
    @GetMapping("viewComment/{title}")
    public List<Comment> viewCommentOnParticularPost(@PathVariable String title){
     return userService.viewAllCommentOfParticularPost(title);
    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }



    @GetMapping("/{title}")
    public PostResponse getPostById(@PathVariable String title){
        return postService.getPostByTitle(title);
    }

}
