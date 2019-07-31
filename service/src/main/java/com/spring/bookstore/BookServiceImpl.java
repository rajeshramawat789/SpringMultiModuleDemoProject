package com.spring.bookstore;

import com.spring.bookstore.dao.BookDao;
import com.spring.bookstore.dao.OrderDao;
import com.spring.bookstore.dto.AddBookRequestDto;
import com.spring.bookstore.dto.BuyBookRequestDto;
import com.spring.bookstore.dto.SearchRequestDto;
import com.spring.bookstore.dto.SearchResponseDto;
import com.spring.bookstore.exception.ApiException;
import com.spring.bookstore.model.Book;
import com.spring.bookstore.model.Orders;
import com.spring.bookstore.util.BookSpecification;
import com.spring.bookstore.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private ThirdPartyService thirdPartyService;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private OrderDao orderDao;

    @Override
    public List<String> searchBookForMediaCoverage(String isbn) throws ApiException {
        String title = bookDao.findBookByIsbn(isbn);
        if (null == title) {
            throw new ApiException(messageUtil.getMessage("isbn.invalid"));
        }

        return thirdPartyService.getMediaCoverages(title).stream()
                .filter(m -> m.getBody().contains(title) || m.getTitle().contains(title))
                .map(m -> m.getTitle()).collect(Collectors.toList());

    }

    @Override
    public void prepareAndSaveBook(AddBookRequestDto addBookRequestDto) throws ApiException {

        if (null != bookDao.findBookByIsbn(addBookRequestDto.getIsbn())) {
            throw new ApiException(messageUtil.getMessage("isbn.already.exists"));
        }

        bookDao.save(new Book(addBookRequestDto.getIsbn(), addBookRequestDto.getTitle(),
                addBookRequestDto.getAuthor(),addBookRequestDto.getPrice(),addBookRequestDto.getNumberOfCopies()));
    }

    @Override
    public SearchResponseDto searchBook(SearchRequestDto searchRequestDto) {

        Page<Book> title = bookDao.findAll(BookSpecification.searchBookWithFilter(
                searchRequestDto.getSearch(),
                searchRequestDto.getColumn()),
                gotoPage(searchRequestDto.getFirst(), searchRequestDto.getRows()));
        return new SearchResponseDto(searchRequestDto.getFirst(), searchRequestDto.getRows(), title.getContent(), title.getNumberOfElements());
    }

    @Override
    public void buyBook(Long id, BuyBookRequestDto buyBookRequestDto) throws ApiException {

        Optional<Book> byId = bookDao.findById(id);
        if(!byId.isPresent())
            throw new ApiException(messageUtil.getMessage("book.id.invalid"));

        Book book = byId.get();

        int availableBooks = book.getQuantity() - buyBookRequestDto.getQuantity();
        if(availableBooks < 0){
            throw new ApiException(messageUtil.getMessage("books.quantity.not.available", book.getQuantity().toString()));
        }

        book.setQuantity(availableBooks);
        bookDao.save(book);

        orderDao.save(new Orders(book.getId(), buyBookRequestDto.getFirstName(),
                buyBookRequestDto.getLastName(), buyBookRequestDto.getQuantity()));

    }

    private PageRequest gotoPage(int page, int size)
    {
        return PageRequest.of(page, size);
    }

}