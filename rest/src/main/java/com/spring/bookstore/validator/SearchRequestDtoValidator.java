package com.spring.bookstore.validator;

import com.spring.bookstore.dto.SearchRequestDto;
import com.spring.bookstore.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SearchRequestDtoValidator implements Validator {

    @Autowired
    private MessageUtil messageUtil;

    @Override
    public boolean supports(Class<?> clazz) {
        return SearchRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SearchRequestDto searchRequestDto = (SearchRequestDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "first", messageUtil.getMessage("search.first.invalid"));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rows", messageUtil.getMessage("search.rows.invalid"));

    }

}
