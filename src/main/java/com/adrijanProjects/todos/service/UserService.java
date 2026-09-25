package com.adrijanProjects.todos.service;

import com.adrijanProjects.todos.dto.UserResponse;
import com.adrijanProjects.todos.entity.User;

import java.nio.file.AccessDeniedException;

public interface UserService {

    UserResponse getUserInfo() throws AccessDeniedException;
    void deleteUser() throws AccessDeniedException;
}
