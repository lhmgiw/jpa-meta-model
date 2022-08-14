package com.lhmgiw.jpametamodel.controller;

import com.lhmgiw.jpametamodel.dto.BookDTO;
import com.lhmgiw.jpametamodel.service.BookService;
import com.lhmgiw.jpametamodel.validator.group.ValidatorOne;
import com.lhmgiw.jpametamodel.validator.group.ValidatorTwo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/books")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookController {
    private BookService bookService;

    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getAllBooks(){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getBook(@PathVariable long id){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getBookById(id));
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> saveBook(@Validated(ValidatorOne.class) @RequestBody BookDTO bookDTO, @RequestParam String username){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.saveBook(bookDTO, username));
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> updateBook(@Validated(ValidatorTwo.class) @RequestBody BookDTO bookDTO, @PathVariable long id, @RequestParam String username){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.updateBook(id, bookDTO, username));
    }

    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> deleteBook(@PathVariable long id, @RequestParam String username){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.deleteBook(id, username));
    }
}
