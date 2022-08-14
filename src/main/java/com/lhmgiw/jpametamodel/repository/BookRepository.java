package com.lhmgiw.jpametamodel.repository;


import com.lhmgiw.jpametamodel.entities.Book;
import com.lhmgiw.jpametamodel.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByCodeAndStatusNot(String code, StatusEnum status);
    Optional<Book> findByIdAndStatusNot(Long id, StatusEnum status);
    List<Book> findAllByStatusNot(StatusEnum status);
}
