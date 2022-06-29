package com.example.demo.service;

import com.example.demo.medel.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public String testService() {

        TodoEntity entity = TodoEntity.builder()
                        .TITLE("My first todo item")
                        .build();

        repository.save(entity);

        TodoEntity savedEntity = repository.findById(entity.getID()).get();

        return savedEntity.getTITLE();
    }

    public List<TodoEntity> create(final TodoEntity entity) {
        // Valdiations
        validate(entity);

        repository.save(entity);

        log.info("Entity Id : {} is saved.", entity.getID());

        return repository.findByUserId(entity.getUSERID());
    }

    private void validate(final TodoEntity entity) {
        if(entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }

        if(entity.getUSERID() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user");
        }
    }

    public List<TodoEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    public List<TodoEntity> update(final TodoEntity entity) {
        validate(entity);

        final Optional<TodoEntity> original = repository.findById(entity.getID());

        original.ifPresent(todo -> {
           todo.setTITLE(entity.getTITLE());
           todo.setDONE(entity.isDONE());
           repository.save(todo);
        });

        return retrieve(entity.getUSERID());
    }

    public List<TodoEntity> delete(final TodoEntity entity) {
        validate(entity);

        try {
            repository.delete(entity);
        } catch(Exception e) {
            log.error("error while deleting entity ", entity.getID(), e);
            throw new RuntimeException("error deleting entity " + entity.getID());
        }

        return retrieve(entity.getUSERID());
    }
}
