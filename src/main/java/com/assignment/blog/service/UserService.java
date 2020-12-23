package com.assignment.blog.service;

import com.assignment.blog.entity.Comment;
import com.assignment.blog.entity.User;
import com.assignment.blog.response.PostResponse;

import java.util.List;

public interface UserService {
    User getUserById(Integer integer);
    User saveUser(User user);
    List<PostResponse> viewOthersPosts(Integer userId);
    void upvoteParticularPost(Integer userId,String title);
    void downVoteParticularPost(Integer userId,String title);
    PostResponse addCommentOnOtherPost(Comment comment,Integer postId);
    List<Comment> viewAllCommentOfParticularPost(String title);
    void deleteComment(Integer commentId);
}
