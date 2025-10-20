package com.example.myprac.garmentsystem.quality.controller;

import com.example.myprac.garmentsystem.quality.model.QualityInspection;
import com.example.myprac.garmentsystem.quality.service.QualityService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/quality/inspections")
@CrossOrigin
public class QualityController {
    private final QualityService service;

    public QualityController(QualityService service) {
        this.service = service;
    }

    @GetMapping
    public List<QualityInspection> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QualityInspection> byId(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<QualityInspection> create(
            @RequestPart("data") QualityInspection inspection,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) throws IOException {
        if (photo != null && !photo.isEmpty()) {
            inspection.setPhoto(photo.getBytes());
            inspection.setPhotoContentType(photo.getContentType());
        }
        QualityInspection saved = service.create(inspection);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<QualityInspection> update(
            @PathVariable Long id,
            @RequestPart("data") QualityInspection inspection,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) throws IOException {
        if (photo != null && !photo.isEmpty()) {
            inspection.setPhoto(photo.getBytes());
            inspection.setPhotoContentType(photo.getContentType());
        }
        return ResponseEntity.ok(service.update(id, inspection));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/photo")
    public ResponseEntity<byte[]> photo(@PathVariable Long id) {
        return service.findById(id)
                .filter(i -> i.getPhoto() != null && i.getPhotoContentType() != null)
                .map(i -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, i.getPhotoContentType())
                        .body(i.getPhoto()))
                .orElse(ResponseEntity.notFound().build());
    }
}
