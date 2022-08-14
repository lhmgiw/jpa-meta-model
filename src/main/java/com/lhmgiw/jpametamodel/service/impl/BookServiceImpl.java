package com.lhmgiw.jpametamodel.service.impl;

import com.lhmgiw.jpametamodel.dto.BookDTO;
import com.lhmgiw.jpametamodel.dto.common.ResponseDTO;
import com.lhmgiw.jpametamodel.entities.Book;
import com.lhmgiw.jpametamodel.enums.StatusEnum;
import com.lhmgiw.jpametamodel.exception.CommonServerException;
import com.lhmgiw.jpametamodel.exception.ObjectAlreadyExistException;
import com.lhmgiw.jpametamodel.exception.ObjectNotFoundException;
import com.lhmgiw.jpametamodel.repository.BookRepository;
import com.lhmgiw.jpametamodel.service.BookService;
import com.lhmgiw.jpametamodel.util.MessageResource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookServiceImpl implements BookService {

    private static final String SAVE_SUCCESS = "book.save.success";
    private static final String SAVE_FAILED = "book.save.failed";
    private static final String UPDATE_SUCCESS = "book.update.success";
    private static final String UPDATE_FAILED = "book.update.failed";
    private static final String DELETE_SUCCESS = "book.delete.success";
    private static final String DELETE_FAILED = "book.delete.failed";
    private static final String NOT_FOUND = "book.not-found";
    private static final String FOUND ="book.found.success";
    private static final String ALREADY_EXIST = "book.code.exist";
    private static final String SUCCESS = "Success";

    private BookRepository bookRepository;
    private ModelMapper modelMapper;
    private MessageResource messageResource;

    @Override
    public Object getAllBooks() {
        try {
            log.info("BookService - getAllBooks() called");
            List<BookDTO> bookList = bookRepository.findAllByStatusNot(StatusEnum.DELETE)
                    .stream()
                    .map(book -> modelMapper.map(book, BookDTO.class))
                    .collect(Collectors.toList());

            log.info("BookService - getAllBooks() completed");
            return new ResponseDTO<>(SUCCESS, messageResource.getMessage(FOUND),
                    bookList);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Object getBookById(Long id) {
        try {
            log.info("BookService - getBookById() called");
            Book book = bookRepository.findByIdAndStatusNot(id, StatusEnum.DELETE).orElseThrow(() ->
                    new ObjectNotFoundException(messageResource.getMessage(NOT_FOUND, new Object[]{id})));

            log.info("BookService - getBookById() completed");
            return new ResponseDTO<>(SUCCESS, messageResource.getMessage(FOUND),
                    modelMapper.map(book, BookDTO.class));

        } catch (ObjectNotFoundException e){
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Object saveBook(BookDTO bookDTO, String username) {
        try {
            log.info("BookService - saveBook() called");
            /* ------- Check book already exist -------- */
            Book book = bookRepository.findByCodeAndStatusNot(bookDTO.getCode(), StatusEnum.DELETE).orElse(null);

            if (book != null)
                throw new ObjectAlreadyExistException(messageResource.getMessage(ALREADY_EXIST, new Object[]{bookDTO.getCode()}));

            /* ------- Map details and save in table -------- */
            book = modelMapper.map(bookDTO, Book.class);
            book.setCreatedBy(username);
            book.setCreatedOn(LocalDateTime.now());
            book.setUpdatedBy(username);
            book.setUpdatedOn(LocalDateTime.now());
            bookRepository.saveAndFlush(book);

            log.info("BookService - saveBook() completed");
            return new ResponseDTO<>(SUCCESS, messageResource.getMessage(SAVE_SUCCESS),
                    modelMapper.map(book, BookDTO.class));

        } catch (ObjectAlreadyExistException e){
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CommonServerException(messageResource.getMessage(SAVE_FAILED));
        }
    }

    @Override
    public Object updateBook(Long id, BookDTO bookDTO, String username) {
        try {
            log.info("BookService - updateBook() called");
            /* -------Find recode -------- */
            Book book = bookRepository.findByIdAndStatusNot(id, StatusEnum.DELETE).orElseThrow(() ->
                    new ObjectNotFoundException(messageResource.getMessage(NOT_FOUND, new Object[]{id})));

            /* ------- Map details and update recode -------- */
            mapToEntity(book, bookDTO);
            book.setUpdatedBy(username);
            book.setUpdatedOn(LocalDateTime.now());
            bookRepository.saveAndFlush(book);

            log.info("BookService - updateBook() completed");
            return new ResponseDTO<>(SUCCESS, messageResource.getMessage(UPDATE_SUCCESS),
                    modelMapper.map(book, BookDTO.class));

        } catch (ObjectNotFoundException e){
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CommonServerException(messageResource.getMessage(UPDATE_FAILED));
        }
    }

    @Override
    public Object deleteBook(Long id, String username) {
        try {
            log.info("BookService - deleteBook() called");
            /* -------Find recode -------- */
            Book book = bookRepository.findByIdAndStatusNot(id, StatusEnum.DELETE).orElseThrow(() ->
                    new ObjectNotFoundException(messageResource.getMessage(NOT_FOUND, new Object[]{id})));

            /* ------- update recode -------- */
            book.setStatus(StatusEnum.DELETE);
            book.setUpdatedBy(username);
            book.setUpdatedOn(LocalDateTime.now());
            bookRepository.saveAndFlush(book);

            log.info("BookService - deleteBook() completed");
            return new ResponseDTO<>(SUCCESS, messageResource.getMessage(DELETE_SUCCESS),
                    modelMapper.map(book, BookDTO.class));

        } catch (ObjectNotFoundException e){
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CommonServerException(messageResource.getMessage(DELETE_FAILED));
        }
    }

    private void mapToEntity(Book book, BookDTO bookDTO){
        book.setName(bookDTO.getName());
        book.setPrice(bookDTO.getPrice());
        book.setAuthor(bookDTO.getAuthor());
        book.setStatus(StatusEnum.getEnum(bookDTO.getStatus()));
    }
}
