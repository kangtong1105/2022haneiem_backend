package com.example.demo.dto;

import com.example.demo.medel.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
    private String id;
    private String title;
    private boolean done;

    public TodoDTO(final TodoEntity entity) {
        this.id = entity.getID();
        this.title = entity.getTITLE();
        this.done = entity.isDONE();
    }

    public static TodoEntity toEntity(final TodoDTO dto) {
        return TodoEntity.builder()
                .ID(dto.getId())
                .TITLE(dto.getTitle())
                .DONE(dto.isDone())
                .build();
    }
}
