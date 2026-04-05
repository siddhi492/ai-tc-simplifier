package com.tc.dto;

import java.util.List;

public class AiResponseDto {
    private List<ClauseResultDto> results;

    public AiResponseDto() {
    }

    public List<ClauseResultDto> getResults() {
        return results;
    }

    public void setResults(List<ClauseResultDto> results) {
        this.results = results;
    }
}