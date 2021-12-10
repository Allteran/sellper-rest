package com.allteran.sellper.controller;

import com.allteran.sellper.domain.User;
import com.allteran.sellper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    @GetMapping()
    public List<User> getUsers() {
        return userService.findAll();
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable("id") User user) {
        return user;
    }

    @PutMapping("{id}")
    public User updateProfile(
            @PathVariable("id") User userFromDb,
            @RequestBody User user) {
        return userService.updateUser(userFromDb, user);
    }

}
