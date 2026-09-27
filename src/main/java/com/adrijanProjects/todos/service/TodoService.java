package com.adrijanProjects.todos.service;

import com.adrijanProjects.todos.dto.TodoRequest;
import com.adrijanProjects.todos.dto.TodoResponse;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface TodoService {

    List<TodoResponse> getAllTodos() throws Exception;

   TodoResponse createTodo(TodoRequest todoRequest) throws AccessDeniedException;

   TodoResponse toggleTodoCompletion(long id) throws AccessDeniedException;

   void deleteTodo(Long id) throws AccessDeniedException;
}
