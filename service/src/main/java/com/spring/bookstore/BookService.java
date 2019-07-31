package com.spring.bookstore;

import com.spring.bookstore.dto.AddBookRequestDto;
import com.spring.bookstore.dto.BuyBookRequestDto;
import com.spring.bookstore.dto.SearchRequestDto;
import com.spring.bookstore.dto.SearchResponseDto;
import com.spring.bookstore.exception.ApiException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookService {

    /**
     * This method is used to search book according to 'ISBN' and
     * and to search title of book in the coverage API
     * @param isbn used to get title of book from database
     * @return List of title of media coverage
     * @throws ApiException if there is not book found by requested ISBN
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    List<String> searchBookForMediaCoverage(String isbn) throws ApiException;

    /**
     * This method is used to prepare and persist Book object to the database
     * @param addBookRequestDto AddBookRequestDto, contains the book information to be store
     * @throws ApiException if there is already a book stored with the same 'ISBN'
     */
    void prepareAndSaveBook(AddBookRequestDto addBookRequestDto) throws ApiException;

    /**
     * This method is used to find books
     * @param searchRequestDto SearchRequestDto, contains information of the book to be search
     * @return SearchResponseDto, contains the expected results
     */
    SearchResponseDto searchBook(SearchRequestDto searchRequestDto);

    /**
     * This method is used to buy a book
     * @param id Long, Primary key of the book to be purchase
     * @param buyBookRequestDto BuyBookRequestDto
     * @throws ApiException if the requested number of copies are not in stock
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    void buyBook(Long id, BuyBookRequestDto buyBookRequestDto) throws ApiException;
}
