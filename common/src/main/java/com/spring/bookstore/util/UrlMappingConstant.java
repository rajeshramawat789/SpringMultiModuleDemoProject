package com.spring.bookstore.util;

/**
 * This class contains URI constants
 */
public class UrlMappingConstant {

    private UrlMappingConstant(){}

    public static final String API_BASE_PATH = "/api/v1";

    public static final String ADD_BOOKS = API_BASE_PATH + "/book";

    public static final String LIST_OF_BOOKS = API_BASE_PATH + "/book/list";

    public static final String SEARCH_MEDIA_COVERAGE = API_BASE_PATH + "/media/coverage/{isbn}";

    public static final String BUY_BOOK = API_BASE_PATH + "/book/buy/{id}";
}
