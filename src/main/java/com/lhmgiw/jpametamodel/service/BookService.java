package com.lhmgiw.jpametamodel.service;

import com.lhmgiw.jpametamodel.dto.BookDTO;

import java.util.Map;

public interface BookService {
    /**
     * Get All Books
     *
     * @param map       -   parameters
     * @param dataTable -   Data table or General list
     * @param draw      -   If datatable is true; Specific draw number
     * @return          -   Response DTO as object
     */
    Object getAllBooks(Map<String, Object> map, Boolean dataTable, Integer draw);

    /**
     * Get a book by id
     *
     * @param id    -   id of the book
     * @return      -   Book DTO
     */
    Object getBookById(Long id);

    /**
     * Save a book
     *
     * @param bookDTO   -   Details of the book
     * @param username  -   logged user's username
     * @return          -   Book DTO
     */
    Object saveBook(BookDTO bookDTO, String username);

    /**
     * Update a book
     *
     * @param id        -   id of the book
     * @param bookDTO   -   updated details
     * @param username  -   logged user's username
     * @return          -   Book DTO
     */
    Object updateBook(Long id, BookDTO bookDTO, String username);

    /**
     * Delete a book
     *
     * @param id        -   id of the book
     * @param username  -   logged user's username
     * @return          -   Book DTO
     */
    Object deleteBook(Long id, String username);
}
