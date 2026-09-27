package com.adrijanProjects.todos.util;

import com.adrijanProjects.todos.entity.User;



import java.nio.file.AccessDeniedException;


public interface FindAuthenticatedUser {

    User getAuthenticatedUser() throws AccessDeniedException;



}
