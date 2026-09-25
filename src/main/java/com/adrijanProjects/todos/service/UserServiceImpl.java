package com.adrijanProjects.todos.service;


import com.adrijanProjects.todos.dto.UserResponse;
import com.adrijanProjects.todos.entity.Authority;
import com.adrijanProjects.todos.entity.User;
import com.adrijanProjects.todos.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getUserInfo() throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser"))
        {
            throw new AccessDeniedException("Authentication required");
        }
        User u=(User) authentication.getPrincipal();

        var authorities = u.getAuthorities();

        return  new UserResponse(
                u.getId(),
                u.getFirstName()+" "+u.getLastName(),
                u.getEmail(),
                u.getAuthorities().stream().map(auth-> (Authority) auth).toList()
        );


    }

    @Override
    public void deleteUser() throws AccessDeniedException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")){
            throw new AccessDeniedException("Authentication required");
        }

        User user=(User) authentication.getPrincipal();


        // isLastAdmin

        if(isLastAdmin(user)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin cannot delete itself");
        }



        userRepository.delete(user);

    }


    private boolean isLastAdmin(User user){

        boolean isAdmin = user.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN"));

        if(isAdmin){

            long adminCount = userRepository.countAdminUsers();

            return adminCount<=1;

        }

        return false;

    }



}
