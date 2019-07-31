package com.spring.bookstore.validator;

import com.spring.bookstore.dto.BuyBookRequestDto;
import com.spring.bookstore.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class BuyBookRequestDtoValidator implements Validator {

    @Autowired
    private MessageUtil messageUtil;

    @Override
    public boolean supports(Class<?> clazz) {
        return BuyBookRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BuyBookRequestDto buyBookRequestDto = (BuyBookRequestDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantity", messageUtil.getMessage("quantity.invalid"));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", messageUtil.getMessage("first.name.invalid"));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", messageUtil.getMessage("last.name.invalid"));

        if(errors.getFieldErrorCount("quantity") == 0 && buyBookRequestDto.getQuantity() <= 0){
            errors.rejectValue("quantity", null, messageUtil.getMessage("quantity.invalid"));
        }

        if(errors.getFieldErrorCount("firstName") == 0 && buyBookRequestDto.getFirstName().length() > 100){
            errors.rejectValue("firstName", null, messageUtil.getMessage("first.name.invalid"));
        }

        if(errors.getFieldErrorCount("lastName") == 0 && buyBookRequestDto.getLastName().length() > 100){
            errors.rejectValue("lastName", null, messageUtil.getMessage("last.name.invalid"));
        }
    }

}
