package com.lhmgiw.jpametamodel.service;

import com.lhmgiw.jpametamodel.dto.BookDTO;

public interface BookService {
    /**
     * Get all books
     *
     * @return  - list of book DTOs
     */
    Object getAllBooks();

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
