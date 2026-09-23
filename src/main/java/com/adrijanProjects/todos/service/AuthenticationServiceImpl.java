package com.adrijanProjects.todos.service;

import com.adrijanProjects.todos.dto.AuthenticationRequest;
import com.adrijanProjects.todos.dto.AuthenticationResponse;
import com.adrijanProjects.todos.dto.RegisterRequest;
import com.adrijanProjects.todos.entity.Authority;
import com.adrijanProjects.todos.entity.User;
import com.adrijanProjects.todos.repository.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;

        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public void register(RegisterRequest registerRequest) throws Exception {

        if(isEmailTaken(registerRequest.getEmail())){
            throw new Exception("Email already taken");
        }

        User user = buildNewUser(registerRequest);
        userRepository.save(user);


    }


    @Override
    @Transactional(readOnly = true)
    public AuthenticationResponse login(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()->new IllegalArgumentException("Invalid email or password"));

        String jwtToken = jwtService.generateToken(new HashMap<>(),user);

        return new AuthenticationResponse(jwtToken);
    }


    private boolean isEmailTaken(String  email){
        return userRepository.findByEmail(email).isPresent();
    }

    private User buildNewUser(RegisterRequest registerRequest) {

        User user = new User();

        user.setId(0);
        user.setFirstName(registerRequest.getFirstname());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setAuthorities(initialAuthority());

        return user;


    }

    private List<Authority> initialAuthority(){
        boolean isFirst = userRepository.count()==0;
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("ROLE_EMPLOYEE"));

        if(isFirst){
            authorities.add(new Authority("ROLE_ADMIN"));
        }

        return authorities;

    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }
}



