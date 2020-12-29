package com.assignment.blog.service;

import com.assignment.blog.entity.Comment;
import com.assignment.blog.entity.User;
import com.assignment.blog.model.UserLogInForm;
import com.assignment.blog.response.PostResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getUserById(Integer integer);
    User getUserByName(String name);
    User signUpUser(User user) throws Exception;
    List<PostResponse> viewOthersPosts();
    void upvoteParticularPost(Integer userId,String title);
    void downVoteParticularPost(Integer userId,String title);
    PostResponse addCommentOnOtherPost(Comment comment,Integer postId);
    List<Comment> viewAllCommentOfParticularPost(String title);
    void deleteComment(Integer commentId);
}
