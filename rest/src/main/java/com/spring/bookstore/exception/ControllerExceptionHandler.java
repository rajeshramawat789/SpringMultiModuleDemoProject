package com.spring.bookstore.exception;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    protected ResponseEntity<ErrorResponseDto> handleApiError(ApiException apiException){

        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(apiException.getClass(), ResponseStatus.class);
        HttpStatus httpStatus;
        if(null != responseStatus){
            httpStatus = responseStatus.value();
        }else {
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(apiException.getMessage());
        return new ResponseEntity<>(errorResponseDto, httpStatus);
    }

    @ExceptionHandler(value = BindException.class)
    protected ResponseEntity<ErrorResource> handleBindingException(BindException ex, HttpServletRequest request) {

        List<FieldErrorResource> fieldErrorResources = new ArrayList<>();

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        fieldErrors.forEach(fieldError -> {
            fieldErrorResources.add(new FieldErrorResource(fieldError.getObjectName(), fieldError.getField(),
                    fieldError.getCode(), fieldError.getRejectedValue()));
        });


        ErrorResource error = new ErrorResource("unprocessableEntity",
                "One of the values provided is not supported or has an invalid format");
        error.setFieldErrors(fieldErrorResources);
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
