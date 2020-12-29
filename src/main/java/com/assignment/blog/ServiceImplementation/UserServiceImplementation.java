package com.assignment.blog.ServiceImplementation;

import com.assignment.blog.entity.Comment;
import com.assignment.blog.entity.Post;
import com.assignment.blog.entity.User;
import com.assignment.blog.repository.CommentRepository;
import com.assignment.blog.repository.PostRepository;
import com.assignment.blog.repository.UserRepository;
import com.assignment.blog.response.PostResponse;
import com.assignment.blog.security.CurrentlyLoggedInUser;
import com.assignment.blog.security.MyUserDetails;
import com.assignment.blog.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository , PostRepository postRepository ,
                                     ModelMapper modelMapper , CommentRepository commentRepository ,
                                     BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository        = userRepository;
        this.postRepository        = postRepository;
        this.modelMapper           = modelMapper;
        this.commentRepository     = commentRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository
                .findById(id)
                .stream()
                .filter(user -> user
                        .getId()
                        .equals(id))
                .findFirst()
                .orElseThrow(NullPointerException::new);
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findByUserName(name);
    }

    @Override
    public User signUpUser(User user) throws Exception {

        if (userRepository.findByUserName(user.getUserName()) != null) {
            throw new Exception("User name already exist");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<PostResponse> viewOthersPosts() {

        User user = CurrentlyLoggedInUser.getUserEntity();
        return postRepository
                .findAll()
                .stream()
                .filter(post -> !post
                        .getUser()
                        .getId()
                        .equals(user.getId()))
                .map(post -> modelMapper.map(post , PostResponse.class))
                .collect(Collectors.toList());
    }

    public void upvoteParticularPost(Integer userId , String title) {
        Post post = postRepository.findByTitle(title);
        User user = getUserById(userId);
        post.setUpvote(user);
        post.removeUserFromDownVote(user);
        post.setTotalUpvote(post
                .getUpvote()
                .size());
        post.setTotalDownVote(post
                .getDownVote()
                .size());
        postRepository.save(post);
    }


    @Override
    public void downVoteParticularPost(Integer userId , String title) {
        Post post = postRepository.findByTitle(title);
        User user = getUserById(userId);
        post.setDownVote(user);
        post.removeUserFromUpvote(user);
        post.setTotalUpvote(post
                .getUpvote()
                .size());
        post.setTotalDownVote(post
                .getDownVote()
                .size());
        postRepository.save(post);

    }

    @Override
    public PostResponse addCommentOnOtherPost(Comment comment , Integer postId) {
        Post post = postRepository
                .findById(postId)
                .stream()
                .filter(post1 -> post1
                        .getId()
                        .equals(postId))
                .findFirst()
                .orElseThrow(NullPointerException::new);
        post
                .getComments()
                .add(comment);
        postRepository.save(post);
        return modelMapper.map(post , PostResponse.class);
    }

    @Override
    public List<Comment> viewAllCommentOfParticularPost(String title) {
        Post post = postRepository.findByTitle(title);
        return post.getComments();
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository
                .findById(commentId)
                .stream()
                .filter(comment1 -> comment1
                        .getId()
                        .equals(commentId))
                .findFirst()
                .orElseThrow(NullPointerException::new);
        commentRepository.delete(comment);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);

        if (user == null) {
            throw new UsernameNotFoundException("User not found " + userName);
        }

        return new MyUserDetails(user.getUserName() , user.getPassword() , new ArrayList<>() , true , true , true ,
                true);

    }
}
