package com.assignment.blog.service;

import com.assignment.blog.entity.Post;
import com.assignment.blog.model.PostForm;
import com.assignment.blog.response.PostResponse;

import java.util.List;

public interface PostService {
    List<Post> getAllPost();
    PostResponse getPostById(Integer id);
    PostResponse savePost(PostForm post, Integer userId);
    PostResponse getPostByTitle(String title);

}
