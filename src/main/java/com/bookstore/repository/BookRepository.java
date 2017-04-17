package com.bookstore.repository;

import com.bookstore.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Savek on 2017-04-16.
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findAll();
}
