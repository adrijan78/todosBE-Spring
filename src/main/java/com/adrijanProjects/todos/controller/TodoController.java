package com.adrijanProjects.todos.controller;


import com.adrijanProjects.todos.dto.TodoRequest;
import com.adrijanProjects.todos.dto.TodoResponse;
import com.adrijanProjects.todos.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
@Tag(name="Todo REST API", description = "Operation for managing user todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }


    @Operation(summary = "Get all todos for user")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<TodoResponse> getAllTodos() throws Exception {
        return todoService.getAllTodos();
    }


    @Operation(summary = "Create todo for user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TodoResponse createTodo(@Valid @RequestBody TodoRequest todoRequest) throws AccessDeniedException {

        return todoService.createTodo(todoRequest);
    }


    @Operation(summary = "Create todo for user")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public TodoResponse toggleTodoCompletion(@PathVariable  long id) throws Exception {
      return todoService.toggleTodoCompletion(id);
    };


    @Operation(summary = "Delete todo for user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable long id) throws AccessDeniedException {
        todoService.deleteTodo(id);
    }


}
