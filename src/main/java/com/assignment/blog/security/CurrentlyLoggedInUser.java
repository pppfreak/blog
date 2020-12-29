package com.assignment.blog.security;

import com.assignment.blog.SpringApplicationContext;
import com.assignment.blog.entity.User;
import com.assignment.blog.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class CurrentlyLoggedInUser {


    public static User getUserEntity() {
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        String userName = authentication.getName();

        UserService userService =
                (UserService) SpringApplicationContext.getBean("userServiceImplementation");
        return userService.getUserByName(userName);

    }

}
