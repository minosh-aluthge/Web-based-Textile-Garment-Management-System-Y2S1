package com.example.myprac.garmentsystem.support.repository;

import com.example.myprac.garmentsystem.support.model.Feedback;
import com.example.myprac.garmentsystem.support.model.FeedbackStatus;
import com.example.myprac.garmentsystem.support.model.FeedbackType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByStatus(FeedbackStatus status);
    List<Feedback> findByType(FeedbackType type);
}
