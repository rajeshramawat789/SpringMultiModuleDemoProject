package com.spring.bookstore.impl;

import com.spring.bookstore.ThirdPartyService;
import com.spring.bookstore.dto.MediaCoverageResponseDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ThirdPartyServiceImpl implements ThirdPartyService {

    @Override
    public List<MediaCoverageResponseDto> getMediaCoverages(String title) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<MediaCoverageResponseDto>> response =
                restTemplate.exchange("https://jsonplaceholder.typicode.com/posts",
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<MediaCoverageResponseDto>>() {
        });

        return response.getBody();
    }
}
