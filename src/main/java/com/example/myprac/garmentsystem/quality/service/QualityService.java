package com.example.myprac.garmentsystem.quality.service;

import com.example.myprac.garmentsystem.quality.model.QualityInspection;
import com.example.myprac.garmentsystem.quality.repository.QualityInspectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QualityService {
    private final QualityInspectionRepository repo;

    public QualityService(QualityInspectionRepository repo) {
        this.repo = repo;
    }

    public List<QualityInspection> findAll() {
        return repo.findAll();
    }

    public Optional<QualityInspection> findById(Long id) {
        return repo.findById(id);
    }

    public Optional<QualityInspection> findByReportId(String reportId) {
        return repo.findByReportId(reportId);
    }

    public QualityInspection create(QualityInspection inspection) {
        // Upsert by batchId: if a record exists for this batch, update it instead of creating a duplicate
        if (inspection.getBatchId() != null) {
            return repo.findByBatchId(inspection.getBatchId())
                .map(existing -> {
                    // Preserve existing reportId unless a new one is explicitly provided
                    if (inspection.getReportId() != null) existing.setReportId(inspection.getReportId());
                    existing.setProduct(inspection.getProduct());
                    existing.setInspector(inspection.getInspector());
                    existing.setInspectionDate(inspection.getInspectionDate());
                    existing.setQualityScore(inspection.getQualityScore());
                    existing.setResult(inspection.getResult());
                    existing.setNotes(inspection.getNotes());
                    if (inspection.getPhoto() != null) existing.setPhoto(inspection.getPhoto());
                    if (inspection.getPhotoContentType() != null) existing.setPhotoContentType(inspection.getPhotoContentType());
                    return repo.save(existing);
                })
                .orElseGet(() -> repo.save(inspection));
        }
        return repo.save(inspection);
    }

    public QualityInspection update(Long id, QualityInspection updated) {
        return repo.findById(id)
                .map(existing -> {
                    // Only overwrite fields that are provided; keep existing otherwise
                    if (updated.getReportId() != null) existing.setReportId(updated.getReportId());
                    if (updated.getBatchId() != null) existing.setBatchId(updated.getBatchId());
                    if (updated.getProduct() != null) existing.setProduct(updated.getProduct());
                    if (updated.getInspector() != null) existing.setInspector(updated.getInspector());
                    if (updated.getInspectionDate() != null) existing.setInspectionDate(updated.getInspectionDate());
                    if (updated.getQualityScore() != null) existing.setQualityScore(updated.getQualityScore());
                    if (updated.getResult() != null) existing.setResult(updated.getResult());
                    if (updated.getNotes() != null) existing.setNotes(updated.getNotes());
                    if (updated.getPhoto() != null) existing.setPhoto(updated.getPhoto());
                    if (updated.getPhotoContentType() != null) existing.setPhotoContentType(updated.getPhotoContentType());
                    return repo.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Inspection not found: " + id));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
