package com.adrijanProjects.todos.repository;

import com.adrijanProjects.todos.dto.TodoResponse;
import com.adrijanProjects.todos.entity.Todo;
import com.adrijanProjects.todos.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {

    List<Todo> findByOwner(User owner);
    Optional<Todo> findByIdAndOwner(long id,User owner);

}
