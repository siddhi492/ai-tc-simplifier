package com.tc.repository;

import com.tc.entity.ClauseAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClauseAnalysisRepository extends JpaRepository<ClauseAnalysis, Long> {
    List<ClauseAnalysis> findByClauseId(Long clauseId);
}