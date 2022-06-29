package com.example.demo.dto;

import com.example.demo.medel.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileDTO {
    private String name;
    private String url;
    private String type;
    private long size;
}
