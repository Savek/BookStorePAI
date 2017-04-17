package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Savek on 2017-04-16.
 */
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    BookController(BookRepository bookRepository,
                   UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    List<Book> readBook() {
        System.out.println(bookRepository.findAll().toString());
        return this.bookRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{bookId}")
    @ResponseBody
    Book readBook(@PathVariable Long bookId) {
        return this.bookRepository.findOne(bookId);
    }

    @RequestMapping(method = RequestMethod.POST)
    void add(@RequestBody Book input) {
        this.bookRepository.save(input);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{bookId}")
    void delete(@PathVariable Long bookId) {
        this.bookRepository.delete(bookId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{bookId}/{status}")
    Book updateStatusAndBorrower(@PathVariable Long bookId, @PathVariable Long status, @RequestBody Map<String, String> requestMap) {
        Book book = bookRepository.findOne(bookId);
        if (status.equals(1L)) {
            System.out.println(requestMap.get("userLogin"));
            System.out.println(userRepository.findByLogin(requestMap.get("userLogin")));
            book.setBorrower(userRepository.findByLogin(requestMap.get("userLogin")));
            book.setStatus(1L);
        } else {
            book.setBorrower(null);
            book.setStatus(0L);
        }
        bookRepository.save(book);
        return book;
    }
}
