package com.assignment.blog.ServiceImplementation;

import com.assignment.blog.entity.User;
import com.assignment.blog.model.PostForm;
import com.assignment.blog.repository.UserRepository;
import com.assignment.blog.entity.Post;
import com.assignment.blog.repository.PostRepository;
import com.assignment.blog.response.PostResponse;
import com.assignment.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImplementation implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PostServiceImplementation(PostRepository postRepository , UserRepository userRepository ,
                                     ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.modelMapper    = modelMapper;
    }


    @Override
    public List<Post> getAllPost() {

        return postRepository.findAll();
    }

    @Override
    public PostResponse getPostById(Integer id) {

        return postRepository
                .findById(id)
                .stream()
                .filter(post -> post
                        .getId()
                        .equals(id))

                .findFirst()
                .map(post -> modelMapper.map(post , PostResponse.class))
                .orElseThrow(NullPointerException::new);
    }

    @Override
    public PostResponse savePost(PostForm postForm , Integer userId) {
        PostResponse postResponse = modelMapper.map(postForm , PostResponse.class);
        if (postResponse.getPostedDate() == null) {
            postResponse.setPostedDate(new Date());
        }
        Post post = modelMapper.map(postResponse , Post.class);

        User user = userRepository
                .findById(userId)
                .stream()
                .filter(user1 -> user1
                        .getId()
                        .equals(userId))
                .findFirst()
                .orElseThrow(NullPointerException::new);
        post.setUser(user);
        postRepository.save(post);
        return postResponse;
    }

    @Override
    public PostResponse getPostByTitle(String title) {
        Post post = postRepository.findByTitle(title);
        return modelMapper.map(post , PostResponse.class);
    }


}
