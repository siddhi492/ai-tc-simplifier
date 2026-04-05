package com.tc.controller;

import com.tc.entity.Clause;
import com.tc.entity.ClauseAnalysis;
import com.tc.entity.Document;
import com.tc.repository.ClauseAnalysisRepository;
import com.tc.repository.ClauseRepository;
import com.tc.repository.DocumentRepository;
import com.tc.service.DocumentProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*")
public class DocumentController {

    private final DocumentProcessingService processingService;
    private final DocumentRepository documentRepository;
    private final ClauseRepository clauseRepository;
    private final ClauseAnalysisRepository clauseAnalysisRepository;

    public DocumentController(DocumentProcessingService processingService,
                              DocumentRepository documentRepository,
                              ClauseRepository clauseRepository,
                              ClauseAnalysisRepository clauseAnalysisRepository) {
        this.processingService = processingService;
        this.documentRepository = documentRepository;
        this.clauseRepository = clauseRepository;
        this.clauseAnalysisRepository = clauseAnalysisRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<Document> upload(@RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok(processingService.uploadAndProcess(file));
    }

    @PostMapping("/text")
    public ResponseEntity<Document> processText(@RequestBody Map<String, String> request) {
        String text = request.get("text");

        if (text == null || text.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(processingService.processRawText(text));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDocumentReport(@PathVariable Long id) {
        java.util.Optional<Document> optionalDocument = documentRepository.findById(id);

        if (optionalDocument.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Document doc = optionalDocument.get();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("document", doc);

        List<Clause> clauses = clauseRepository.findByDocumentId(id);
        response.put("clauses", clauses);

        List<List<ClauseAnalysis>> analyses = new ArrayList<>();
        for (Clause c : clauses) {
            analyses.add(clauseAnalysisRepository.findByClauseId(c.getId()));
        }

        response.put("analysis", analyses);

        return ResponseEntity.ok(response);
    }
}