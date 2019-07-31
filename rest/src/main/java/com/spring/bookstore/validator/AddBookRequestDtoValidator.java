package com.spring.bookstore.validator;

import com.spring.bookstore.dto.AddBookRequestDto;
import com.spring.bookstore.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AddBookRequestDtoValidator implements Validator {

    @Autowired
    private MessageUtil messageUtil;

    @Override
    public boolean supports(Class<?> clazz) {
        return AddBookRequestDtoValidator.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AddBookRequestDto addBookRequestDto = (AddBookRequestDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isbn", messageUtil.getMessage("book.isbn.not.null"));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", messageUtil.getMessage("book.title.not.null"));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "author", messageUtil.getMessage("book.author.not.null"));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", messageUtil.getMessage("book.price.not.null"));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numberOfCopies", messageUtil.getMessage("book.numberOfCopies.not.null"));

    }
}
