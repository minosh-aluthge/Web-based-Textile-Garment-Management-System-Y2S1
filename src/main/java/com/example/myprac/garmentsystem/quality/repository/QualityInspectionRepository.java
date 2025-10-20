package com.example.myprac.garmentsystem.quality.repository;

import com.example.myprac.garmentsystem.quality.model.QualityInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QualityInspectionRepository extends JpaRepository<QualityInspection, Long> {
    Optional<QualityInspection> findByReportId(String reportId);
    Optional<QualityInspection> findByBatchId(String batchId);
}
