package com.tc.service;

import com.tc.dto.AiResponseDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AiService {

    private final RestTemplate restTemplate = new RestTemplate();

    public AiResponseDto processDocumentText(String text) {
        String url = "http://localhost:8000/process";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request =
                new HttpEntity<>(Map.of("text", text), headers);

        ResponseEntity<AiResponseDto> response =
                restTemplate.postForEntity(url, request, AiResponseDto.class);

        return response.getBody();
    }
}