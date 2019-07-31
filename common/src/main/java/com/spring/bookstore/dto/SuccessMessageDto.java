package com.spring.bookstore.dto;

public class SuccessMessageDto {

    private String message;

    public SuccessMessageDto(){}

    public SuccessMessageDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
