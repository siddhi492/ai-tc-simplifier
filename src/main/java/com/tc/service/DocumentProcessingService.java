package com.tc.service;

import com.tc.dto.AiResponseDto;
import com.tc.dto.ClauseResultDto;
import com.tc.entity.Clause;
import com.tc.entity.ClauseAnalysis;
import com.tc.entity.Document;
import com.tc.repository.ClauseAnalysisRepository;
import com.tc.repository.ClauseRepository;
import com.tc.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class DocumentProcessingService {

    private final DocumentRepository documentRepository;
    private final ClauseRepository clauseRepository;
    private final ClauseAnalysisRepository clauseAnalysisRepository;
    private final AiService aiService;

    public DocumentProcessingService(DocumentRepository documentRepository,
                                     ClauseRepository clauseRepository,
                                     ClauseAnalysisRepository clauseAnalysisRepository,
                                     AiService aiService) {
        this.documentRepository = documentRepository;
        this.clauseRepository = clauseRepository;
        this.clauseAnalysisRepository = clauseAnalysisRepository;
        this.aiService = aiService;
    }

    public Document processRawText(String content) {
        Document document = new Document();
        document.setFileName("manual_input");
        document.setStatus("PROCESSING");
        document.setUploadedAt(LocalDateTime.now());

        document = documentRepository.save(document);

        AiResponseDto aiResponse = aiService.processDocumentText(content);

        int index = 1;

        if (aiResponse != null && aiResponse.getResults() != null) {
            for (ClauseResultDto result : aiResponse.getResults()) {
                Clause clause = new Clause();
                clause.setDocumentId(document.getId());
                clause.setClauseOrder(index++);
                clause.setText(result.getClause());

                clause = clauseRepository.save(clause);

                ClauseAnalysis analysis = new ClauseAnalysis();
                analysis.setClauseId(clause.getId());
                analysis.setCategory(result.getCategory());
                analysis.setRisk(result.getRisk());
                analysis.setSummary(result.getSummary());

                clauseAnalysisRepository.save(analysis);
            }
        }

        document.setStatus("COMPLETED");
        return documentRepository.save(document);
    }

    public Document uploadAndProcess(MultipartFile file) throws IOException {
        String content = new String(file.getBytes());
        return processRawText(content);
    }
}