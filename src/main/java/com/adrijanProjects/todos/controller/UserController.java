package com.adrijanProjects.todos.controller;

import com.adrijanProjects.todos.dto.PasswordUpdateRequest;
import com.adrijanProjects.todos.dto.UserResponse;
import com.adrijanProjects.todos.entity.User;
import com.adrijanProjects.todos.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;


@Tag(name="User REST API Endpoints")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(summary = "User information")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/info")
    public UserResponse getUserInfo() throws AccessDeniedException {

        return userService.getUserInfo();
    }


    @Operation(summary = "Delete user")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void deleteUser() throws AccessDeniedException {
        userService.deleteUser();
    }


    @Operation(summary = "Password update")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/password-change")
    public void passwordUpdate(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest) throws Exception{
        userService.updatePassword(passwordUpdateRequest);
    }
}
