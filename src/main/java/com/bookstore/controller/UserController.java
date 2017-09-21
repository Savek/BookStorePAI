package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.model.User;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Created by Savek on 2017-04-16.
 */

@Controller
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    UserController(UserRepository userRepository,
                   RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @RequestMapping("/user")
    @ResponseBody
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getUsers() {
        List<User> users = this.userRepository.findAll();
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    void add(@RequestBody User input) {
        input.setRole(roleRepository.findOne(3l));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        input.setPassword(passwordEncoder.encode(input.getPassword()));

        this.userRepository.save(input);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{userId}")
    void delete(@PathVariable Long userId) {
        this.userRepository.delete(userId);
    }
}
