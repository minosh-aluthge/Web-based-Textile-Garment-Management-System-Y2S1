package com.example.myprac.garmentsystem.support.controller;

import com.example.myprac.garmentsystem.support.model.*;
import com.example.myprac.garmentsystem.support.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support/feedback")
@CrossOrigin
public class FeedbackController {

    private final FeedbackService service;

    public FeedbackController(FeedbackService service) { this.service = service; }

    @GetMapping
    public List<Feedback> list(@RequestParam(value = "status", required = false) FeedbackStatus status,
                               @RequestParam(value = "type", required = false) FeedbackType type){
        if (status != null) return service.listByStatus(status);
        if (type != null) return service.listByType(type);
        return service.list();
    }

    @PostMapping
    public Feedback create(@RequestBody Feedback f){ return service.create(f); }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> get(@PathVariable Long id){
        return service.get(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Feedback update(@PathVariable Long id, @RequestBody Feedback f){ return service.update(id, f); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){ service.delete(id); return ResponseEntity.noContent().build(); }

    // Actions
    @PostMapping("/{id}/categorize")
    public Feedback categorize(@PathVariable Long id, @RequestParam FeedbackType type){ return service.categorize(id, type); }

    @PostMapping("/{id}/status")
    public Feedback setStatus(@PathVariable Long id, @RequestParam FeedbackStatus status){ return service.setStatus(id, status); }

    @PostMapping("/{id}/spam")
    public Feedback spam(@PathVariable Long id){ return service.markSpam(id); }

    @PostMapping("/{id}/clarify")
    public Feedback clarify(@PathVariable Long id){ return service.requestClarification(id); }
}
