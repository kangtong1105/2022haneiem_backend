package com.example.demo.service;
import java.io.IOException;
import java.util.stream.Stream;

import com.example.demo.medel.FileEntity;
import com.example.demo.persistence.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
@Slf4j
@Service
public class FileStorageService {
    @Autowired
    private FileRepository fileDBRepository;
    public FileEntity store(MultipartFile file, String owner) throws IOException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileEntity FileDB = new FileEntity(null, fileName, file.getContentType(), owner, file.getBytes());
        FileDB = fileDBRepository.save(FileDB);

        log.info("FILEDB: Store file:" + FileDB.getId() + " by user:" + owner);

        return FileDB;
    }
    public FileEntity getFile(String id, String owner) {

        log.info("FILEDB: Search file:" + id + " by user:" + owner);

        return fileDBRepository.findById(id).get();
    }

    public Stream<FileEntity> getAllFiles(String owner) {

        log.info("FILEDB: get all file by user:" + owner);

        return fileDBRepository.findAll().stream();
    }
}