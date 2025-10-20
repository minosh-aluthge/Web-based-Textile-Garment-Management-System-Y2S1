package com.example.myprac.garmentsystem.support.service;

import com.example.myprac.garmentsystem.support.model.*;
import com.example.myprac.garmentsystem.support.repository.FeedbackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FeedbackService {
    private final FeedbackRepository repo;

    public FeedbackService(FeedbackRepository repo) { this.repo = repo; }

    public List<Feedback> list(){ return repo.findAll(); }
    public List<Feedback> listByStatus(FeedbackStatus status){ return repo.findByStatus(status); }
    public List<Feedback> listByType(FeedbackType type){ return repo.findByType(type); }
    public Optional<Feedback> get(Long id){ return repo.findById(id); }
    public Feedback create(Feedback f){ return repo.save(f); }
    public Feedback update(Long id, Feedback req){
        return repo.findById(id).map(f -> {
            if (req.getType() != null) f.setType(req.getType());
            if (req.getStatus() != null) f.setStatus(req.getStatus());
            if (req.getAssignedTo() != null) f.setAssignedTo(req.getAssignedTo());
            if (req.getPriority() != null) f.setPriority(req.getPriority());
            if (req.getSubmissionDate() != null) f.setSubmissionDate(req.getSubmissionDate());
            if (req.getSubmittedBy() != null) f.setSubmittedBy(req.getSubmittedBy());
            if (req.getSubject() != null) f.setSubject(req.getSubject());
            if (req.getDescription() != null) f.setDescription(req.getDescription());
            return repo.save(f);
        }).orElseThrow(() -> new IllegalArgumentException("Feedback not found: " + id));
    }
    public void delete(Long id){ repo.deleteById(id); }

    public Feedback categorize(Long id, FeedbackType type){
        return repo.findById(id).map(f -> { f.setType(type); return repo.save(f); })
                .orElseThrow(() -> new IllegalArgumentException("Feedback not found: " + id));
    }
    public Feedback setStatus(Long id, FeedbackStatus status){
        return repo.findById(id).map(f -> { f.setStatus(status); return repo.save(f); })
                .orElseThrow(() -> new IllegalArgumentException("Feedback not found: " + id));
    }
    public Feedback markSpam(Long id){
        return repo.findById(id).map(f -> { f.setType(FeedbackType.Spam); f.setStatus(FeedbackStatus.Removed); return repo.save(f); })
                .orElseThrow(() -> new IllegalArgumentException("Feedback not found: " + id));
    }
    public Feedback requestClarification(Long id){
        return setStatus(id, FeedbackStatus.ClarificationRequested);
    }
}
