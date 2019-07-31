package com.spring.bookstore;

import com.spring.bookstore.dto.MediaCoverageResponseDto;

import java.util.List;

public interface ThirdPartyService {

    List<MediaCoverageResponseDto> getMediaCoverages(String title);
}
