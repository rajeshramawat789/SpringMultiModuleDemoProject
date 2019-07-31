package com.spring.bookstore.util;

import com.spring.bookstore.model.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class BookSpecification {

    public static Specification<Book> searchBookWithFilter(
            String searchTerm, String columnName) {
        return (root, query, cb) -> {

        	Predicate predicate;
            if(CommonConstants.TITLE.equals(columnName)){
                predicate = cb.like(cb.lower(root.get("title")), getContainsLikePattern(searchTerm));
            }else if(CommonConstants.AUTHOR.equals(columnName)){
                predicate = cb.like(cb.lower(root.get("author")), getContainsLikePattern(searchTerm));
            }else {
                predicate = cb.equal(cb.lower(root.get("isbn")), searchTerm);
            }

            return predicate;
        };
    }

    private static String getContainsLikePattern(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return "%";
        }
        else {
            return "%" + searchTerm.toLowerCase() + "%";
        }
    }

}
