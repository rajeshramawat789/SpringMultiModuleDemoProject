package com.spring.bookstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchResponseDto {

    private Integer first;
    private Integer rows;
    private List data;
    private Integer totalRecords;

    public SearchResponseDto(){}

    public SearchResponseDto(Integer first, Integer rows, List data, Integer totalRecords) {
        this.first = first;
        this.rows = rows;
        this.data = data;
        this.totalRecords = totalRecords;
    }
}
