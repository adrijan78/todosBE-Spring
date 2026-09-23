package com.adrijanProjects.todos.controller;


import com.adrijanProjects.todos.dto.AuthenticationRequest;
import com.adrijanProjects.todos.dto.AuthenticationResponse;
import com.adrijanProjects.todos.dto.RegisterRequest;
import com.adrijanProjects.todos.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name="Authentication REST API Endpoints")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @Operation(summary = "Register a user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequest registerRequest) throws Exception{
        authenticationService.register(registerRequest);
    }


    @Operation(summary = "Login a user", description = "submit email & password to authenticate the user")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public AuthenticationResponse login(@Valid @RequestBody AuthenticationRequest request) {

        return authenticationService.login(request);

    }


}
