package com.spring.bookstore.controller;

import com.spring.bookstore.BookService;
import com.spring.bookstore.dto.AddBookRequestDto;
import com.spring.bookstore.dto.BuyBookRequestDto;
import com.spring.bookstore.dto.SearchRequestDto;
import com.spring.bookstore.dto.SearchResponseDto;
import com.spring.bookstore.dto.SuccessMessageDto;
import com.spring.bookstore.exception.ApiException;
import com.spring.bookstore.util.MessageUtil;
import com.spring.bookstore.util.UrlMappingConstant;
import com.spring.bookstore.validator.AddBookRequestDtoValidator;
import com.spring.bookstore.validator.BuyBookRequestDtoValidator;
import com.spring.bookstore.validator.SearchRequestDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This controller handles the http requests which are related to adding a book
 */
@RestController
public class BookController {

    @Autowired
    private AddBookRequestDtoValidator addBookRequestDtoValidator;

    @Autowired
    private SearchRequestDtoValidator searchRequestDtoValidator;

    @Autowired
    private BuyBookRequestDtoValidator buyBookRequestDtoValidator;

    @Autowired
    private BookService bookService;

    @Autowired
    private MessageUtil messageUtil;

    /**
     * Request Type : Post
     * This request is used to persist books information to database
     * @param addBookRequestDto AddBookRequestDto, Request DTO
     * @param bindingResult used to store error information
     * @return Success message
     * @throws BindException if DTO contains any invalid information
     * @throws ApiException
     */
    @PostMapping(value = UrlMappingConstant.ADD_BOOKS)
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessMessageDto addBook(@RequestBody AddBookRequestDto addBookRequestDto, BindingResult bindingResult) throws BindException, ApiException {

        addBookRequestDtoValidator.validate(addBookRequestDto, bindingResult);
        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        bookService.prepareAndSaveBook(addBookRequestDto);
        return new SuccessMessageDto(messageUtil.getMessage("add.book.success"));
    }

    /**
     * Request Type : Post
     * This request is used to search records
     * @param searchRequestDto requested search information
     * @param bindingResult used to store error information
     * @return SearchResponseDto, contains the expected results
     * @throws BindException if DTO contains any invalid information
     */
    @PostMapping(value = UrlMappingConstant.LIST_OF_BOOKS)
    @ResponseStatus(HttpStatus.OK)
    public SearchResponseDto getBooks(@RequestBody SearchRequestDto searchRequestDto, BindingResult bindingResult) throws BindException {

        searchRequestDtoValidator.validate(searchRequestDto, bindingResult);
        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        return bookService.searchBook(searchRequestDto);
    }

    /**
     * Request Type : GET
     * This API is used to get title of media coverages
     * @param isbn String, ISBN of the book
     * @return List of titles of media coverages for the particular book
     * @throws ApiException if requested isbn is invalid or not exists in the database
     */
    @GetMapping(value= UrlMappingConstant.SEARCH_MEDIA_COVERAGE)
    @ResponseStatus(HttpStatus.OK)
    public List<String> searchBookInMediaCoverage(@PathVariable("isbn") String isbn) throws ApiException {

        if(null == isbn || isbn.isEmpty()){
            throw new ApiException(messageUtil.getMessage("isbn.invalid"));
        }

        return bookService.searchBookForMediaCoverage(isbn);
    }

    /**
     *
     * @param id Long, Primary key of book to be purchased
     * @param buyBookRequestDto BuyBookRequestDto
     * @param bindingResult BindingResult, To store field errors
     * @return Success message if order purchased
     * @throws ApiException in case if any invalid information requested
     * @throws BindException to throw field errors
     */
    @PostMapping(value= UrlMappingConstant.BUY_BOOK)
    @ResponseStatus(HttpStatus.CREATED)
    public synchronized SuccessMessageDto buyBooks(@PathVariable("id") Long id,
                         @RequestBody BuyBookRequestDto buyBookRequestDto, BindingResult bindingResult)
            throws ApiException, BindException {

        if(null == id){
            throw new ApiException(messageUtil.getMessage("book.id.invalid"));
        }

        buyBookRequestDtoValidator.validate(buyBookRequestDto, bindingResult);
        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        bookService.buyBook(id, buyBookRequestDto);

        return new SuccessMessageDto(messageUtil.getMessage("buy.book.success"));
    }
}
