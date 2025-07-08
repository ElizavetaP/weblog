package ru.yandex.weblog.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@RestController
public class ImageController {

    @Value("${image.folder}")
    private String imageFolder;

    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable(name = "id") String id) {
        try {
            String path = imageFolder + "/" + id + ".jpg";
            Resource resource = new ClassPathResource(path);

            if (!resource.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            InputStream inputStream = resource.getInputStream();
            byte[] imageBytes = inputStream.readAllBytes();
            inputStream.close();

            String contentType = "application/octet-stream";
            try {
                contentType = Files.probeContentType(resource.getFile().toPath());
            } catch (IOException ignored) {}

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}