package com.adrijanProjects.todos.service;

import com.adrijanProjects.todos.dto.AuthenticationRequest;
import com.adrijanProjects.todos.dto.AuthenticationResponse;
import com.adrijanProjects.todos.dto.RegisterRequest;

public interface AuthenticationService {

    void register(RegisterRequest registerRequest) throws Exception;

    AuthenticationResponse login(AuthenticationRequest request);

}
