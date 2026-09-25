package com.adrijanProjects.todos.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionHandlers {





    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponses> handleException(ResponseStatusException exc){

        return buildResponseEntity(exc,HttpStatus.valueOf(exc.getStatusCode().value()));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponses> handleException(Exception exc){

        return buildResponseEntity(exc,HttpStatus.BAD_REQUEST);

    }


    private ResponseEntity<ExceptionResponses> buildResponseEntity(Exception exc, HttpStatus status){
        ExceptionResponses err = new ExceptionResponses();
        err.setStatus(status.value());
        err.setMessage(exc.getMessage());
        err.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(err,status);
    }

}
