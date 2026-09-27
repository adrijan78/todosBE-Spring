package com.adrijanProjects.todos.service;

import com.adrijanProjects.todos.dto.UserResponse;

import java.util.List;

public interface AdminService {

    List<UserResponse> getAllUsers();
    UserResponse promoteToAdmin(long id);

    void delteNonAdminUser(long id);
}
