package com.adrijanProjects.todos.service;

import com.adrijanProjects.todos.dto.TodoRequest;
import com.adrijanProjects.todos.dto.TodoResponse;
import com.adrijanProjects.todos.entity.Todo;
import com.adrijanProjects.todos.entity.User;
import com.adrijanProjects.todos.repository.TodoRepository;
import com.adrijanProjects.todos.util.FindAuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;



    public TodoServiceImpl(TodoRepository todoRepository, FindAuthenticatedUser findAuthenticatedUser) {
        this.todoRepository = todoRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> getAllTodos() throws Exception {
        User currentUser=findAuthenticatedUser.getAuthenticatedUser();
        return todoRepository.findByOwner(currentUser)
                .stream()
                .map(this::convertToTodoResponse)
                .toList();
    }

    @Override
    @Transactional
    public TodoResponse createTodo(TodoRequest todoRequest) throws AccessDeniedException {

        User currentUser = findAuthenticatedUser.getAuthenticatedUser();

        Todo todo = new Todo(
                todoRequest.getTitle(),
                todoRequest.getDescription(),
                todoRequest.getPriority(),
                false,
                currentUser
        );


        Todo savedTodo = todoRepository.save(todo);

        TodoResponse todoResponse=convertToTodoResponse(savedTodo);

        return todoResponse;

    }

    @Override
    @Transactional
    public TodoResponse toggleTodoCompletion(long id) throws AccessDeniedException {
        User currentUser = findAuthenticatedUser.getAuthenticatedUser();
        Optional<Todo>todo= Optional.of(todoRepository.findByIdAndOwner(id, currentUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found")));

        todo.get().setComplete(!todo.get().isComplete());

        Todo updatedTodo=todoRepository.save(todo.get());

        return convertToTodoResponse(updatedTodo);

    }

    @Override
    @Transactional
    public void deleteTodo(Long id) throws AccessDeniedException {
        User currentUser = findAuthenticatedUser.getAuthenticatedUser();
        Optional<Todo>todo= Optional.of(todoRepository.findByIdAndOwner(id, currentUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found")));

        todoRepository.delete(todo.get());
    }


    private TodoResponse convertToTodoResponse(Todo savedTodo){

        return new TodoResponse(
                savedTodo.getId(),
                savedTodo.getTitle(),
                savedTodo.getDescription(),
                savedTodo.getPriority(),
                savedTodo.isComplete()
        );

    }



}
