package com.bookstore.repository;

import com.bookstore.model.Book;
import com.bookstore.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Savek on 2017-04-16.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAll();

    User findByLogin(String login);
    User findById(Long userId);
}
