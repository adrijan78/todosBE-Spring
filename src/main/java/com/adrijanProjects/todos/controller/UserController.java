package com.adrijanProjects.todos.controller;

import com.adrijanProjects.todos.dto.UserResponse;
import com.adrijanProjects.todos.entity.User;
import com.adrijanProjects.todos.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;


@Tag(name="User REST API Endpoints")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/info")
    public UserResponse getUserInfo() throws AccessDeniedException {

        return userService.getUserInfo();
    }


    @DeleteMapping
    public void deleteUser() throws AccessDeniedException {
        userService.deleteUser();
    }
}
