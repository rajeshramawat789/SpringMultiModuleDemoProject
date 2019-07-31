package com.spring.bookstore.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@PropertySource(value = {
        "classpath:message.properties",
        "classpath:validation.properties",
        "classpath:exception.properties"
})
public class MessageUtil {

    @Autowired
    private MessageSource messageSource;

    private static final Locale LOCALE = new Locale("ENG");

    public String getMessage(String key){
        return messageSource.getMessage(key, null, LOCALE);
    }

    public String getMessage(String key, String... args) {
        return messageSource.getMessage(key, args, LOCALE);
    }
}
