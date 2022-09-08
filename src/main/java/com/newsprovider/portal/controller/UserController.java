package com.newsprovider.portal.controller;

import com.newsprovider.portal.model.User;
import com.newsprovider.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Object> register(User user) {
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
