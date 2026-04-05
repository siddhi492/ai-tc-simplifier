package com.tc.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "clause_analysis")
public class ClauseAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clause_id", nullable = false)
    private Long clauseId;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String risk;

    @Column(columnDefinition = "TEXT")
    private String summary;

    public ClauseAnalysis() {
    }

    public ClauseAnalysis(Long id, Long clauseId, String category, String risk, String summary) {
        this.id = id;
        this.clauseId = clauseId;
        this.category = category;
        this.risk = risk;
        this.summary = summary;
    }

    public Long getId() {
        return id;
    }

    public Long getClauseId() {
        return clauseId;
    }

    public String getCategory() {
        return category;
    }

    public String getRisk() {
        return risk;
    }

    public String getSummary() {
        return summary;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClauseId(Long clauseId) {
        this.clauseId = clauseId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}