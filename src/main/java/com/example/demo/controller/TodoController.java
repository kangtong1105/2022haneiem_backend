package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.medel.TodoEntity;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
        value = "/todo",
        method = {
                RequestMethod.GET,
                RequestMethod.PUT,
                RequestMethod.POST,
                RequestMethod.DELETE
        })
public class TodoController {

    @Autowired
    private TodoService service;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/post")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto, @AuthenticationPrincipal String userId) {

        try {
            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setID(null);
            entity.setUSERID(userId);

            return create_OKresponse(service.create(entity));
        } catch(Exception e) {
            return create_BADresponse(e);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId) {
        return create_OKresponse(service.retrieve(userId));
    }

    @PutMapping("/put")
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto, @AuthenticationPrincipal String userId) {

        TodoEntity entity = TodoDTO.toEntity(dto);
        entity.setUSERID(userId);

        return create_OKresponse(service.update(entity));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto, @AuthenticationPrincipal String userId) {

        try {
            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setUSERID(userId);

            return create_OKresponse(service.delete(entity));
        } catch(Exception e) {
            return create_BADresponse(e);
        }
    }

    private ResponseEntity<?> create_OKresponse(List<TodoEntity> entity) {
        List<TodoDTO> dtos = entity.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
        response.setLength(dtos.size());
        return ResponseEntity.ok().body(response);
    }

    private ResponseEntity<?> create_BADresponse(Exception e) {
        String error = e.getMessage();
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
        return ResponseEntity.badRequest().body(response);
    }
}
