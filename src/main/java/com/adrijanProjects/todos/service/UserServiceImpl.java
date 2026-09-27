package com.adrijanProjects.todos.service;


import com.adrijanProjects.todos.dto.PasswordUpdateRequest;
import com.adrijanProjects.todos.dto.UserResponse;
import com.adrijanProjects.todos.entity.Authority;
import com.adrijanProjects.todos.entity.User;
import com.adrijanProjects.todos.repository.UserRepository;
import com.adrijanProjects.todos.util.FindAuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, FindAuthenticatedUser findAuthenticatedUser, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getUserInfo() throws AccessDeniedException {
        User u = findAuthenticatedUser.getAuthenticatedUser();

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

        User user = findAuthenticatedUser.getAuthenticatedUser();


        // isLastAdmin

        if(isLastAdmin(user)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin cannot delete itself");
        }



        userRepository.delete(user);

    }

    @Override
    @Transactional
    public void updatePassword(PasswordUpdateRequest passwordUpdateRequest) throws AccessDeniedException {
        User user = findAuthenticatedUser.getAuthenticatedUser();

        if(!isOldPasswordCorrect(user.getPassword(),passwordUpdateRequest.getOldPassword())){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Current password is incorrect");

        }

        if(!isNewPasswordConfirmed(passwordUpdateRequest.getNewPassword(),passwordUpdateRequest.getNewPassword2())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"New passwords do not match");
        }

        if(!isNewPasswordDifferent(passwordUpdateRequest.getOldPassword(),passwordUpdateRequest.getNewPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Old and new passwords must be different");
        }

        user.setPassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
        userRepository.save(user);

    }


    private boolean isOldPasswordCorrect(String currentPassword,String oldPassword){
        return passwordEncoder.matches(oldPassword,currentPassword);
    }

    private boolean isNewPasswordConfirmed(String newPassword, String confirmedPassword){
        return newPassword.equals(confirmedPassword);
    }

    private boolean isNewPasswordDifferent(String oldPassword,String newPassword){
        return !oldPassword.equals(newPassword);
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
