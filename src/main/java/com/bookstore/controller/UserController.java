package com.bookstore.controller;

import com.bookstore.model.User;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}")
    @ResponseBody
    public ResponseEntity<User> user(@PathVariable Long userId) {
        User user = userRepository.findById(userId);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    void add(@RequestBody User input) {
        input.setRole(roleRepository.findOne(3l));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        input.setPassword(passwordEncoder.encode(input.getPassword()));

        userRepository.save(input);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{userId}")
    void delete(@PathVariable Long userId) {
        userRepository.delete(userId);
    }
}
