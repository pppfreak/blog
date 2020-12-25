package com.assignment.blog.controller;

import com.assignment.blog.entity.Post;
import com.assignment.blog.ServiceImplementation.PostServiceImplementation;
import com.assignment.blog.model.PostForm;
import com.assignment.blog.response.PostResponse;
import com.assignment.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostServiceImplementation postService) {
        this.postService = postService;
    }


    @GetMapping
    public List<Post> getAllPosts(){
        return postService.getAllPost();
    }

    @GetMapping("/{title}")
    public PostResponse getPostById(@PathVariable String title){
        return postService.getPostByTitle(title);
    }

//    @PostMapping("/{userId}")
//    public PostResponse createPost(@RequestBody PostForm post, @PathVariable Integer userId){
//        return postService.savePost(post ,userId);
//    }
}
