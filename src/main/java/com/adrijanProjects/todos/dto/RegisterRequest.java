package com.adrijanProjects.todos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotEmpty(message = "First name is mandatory")
    @Size(min=3, max=30,message = "First name must be at least 3 characters long")
    private String firstname;

    @NotEmpty(message = "Last name is mandatory")
    @Size(min=3, max=30,message = "Last name must be at least 3 characters long")
    private String lastName;

    @NotEmpty(message = "Email is mandatory")
    @Email
    private String email;

    @NotEmpty(message = "First name is mandatory")
    @Size(min=5, max=30,message = "First name must be at least 3 characters long")
    private String password;

    public RegisterRequest(String firstname, String lastName, String email, String password) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
