package com.spring.bookstore.dao;

import com.spring.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDao extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    /**
     * Used to get title of book by using 'ISBN'
     * @param isbn String, to fetch title of the book
     * @return title of the book
     */
    @Query("select b.title from Book b where b.isbn =:isbn")
    String findBookByIsbn(@Param("isbn") String isbn);

}
