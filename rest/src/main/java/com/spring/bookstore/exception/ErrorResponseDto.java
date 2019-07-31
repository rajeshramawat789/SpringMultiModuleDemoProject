package com.spring.bookstore.exception;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponseDto {

    private String message;

    public ErrorResponseDto(String message){
        super();
        this.message = message;
    }

}
