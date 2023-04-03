package com.example.courseworkthree.controllers;

import com.example.courseworkthree.services.FilesService;
import com.example.courseworkthree.services.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files")
@Tag(name = "Файлы",description = "Экспорт/импорт файлов")
public class FilesController {

    private final FilesService filesService;
    private final SocksService socksService;


    public FilesController(FilesService filesService, SocksService socksService) {
        this.filesService = filesService;
        this.socksService = socksService;
    }

    @GetMapping(value = "/export/socks")
    @Operation(summary = "Экспорт носков в формате .json")
    public ResponseEntity<InputStreamResource> downloadFile() throws FileNotFoundException {
        File file = filesService.getDataFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\"socks.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/import/socks", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Импорт файла носков в формате .json")
    public ResponseEntity<Void> uploadFile(@RequestParam MultipartFile multipartFile) {
        filesService.cleanDataFile();
        File file = filesService.getDataFile();
        try(FileOutputStream fos = new FileOutputStream(file)) {
            IOUtils.copy(multipartFile.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
           e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
