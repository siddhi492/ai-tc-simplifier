package com.tc.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "clauses")
public class Clause {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document_id", nullable = false)
    private Long documentId;

    @Column(name = "clause_order")
    private Integer clauseOrder;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    public Clause() {
    }

    public Clause(Long id, Long documentId, Integer clauseOrder, String text) {
        this.id = id;
        this.documentId = documentId;
        this.clauseOrder = clauseOrder;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public Integer getClauseOrder() {
        return clauseOrder;
    }

    public String getText() {
        return text;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public void setClauseOrder(Integer clauseOrder) {
        this.clauseOrder = clauseOrder;
    }

    public void setText(String text) {
        this.text = text;
    }
}