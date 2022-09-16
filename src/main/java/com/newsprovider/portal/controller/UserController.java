package com.newsprovider.portal.controller;

import com.newsprovider.portal.model.Category;
import com.newsprovider.portal.model.User;
import com.newsprovider.portal.repository.LoggedUserRepository;
import com.newsprovider.portal.service.CategoryService;
import com.newsprovider.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LoggedUserRepository loggedUserRepository;


    @PostMapping
    public ResponseEntity<?> register(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(Long id) {
        User user = userService.getById(id);
        userService.delete(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add-category/{id}")
    public ResponseEntity<?> addCategory(@PathVariable Integer id) {
        User user = loggedUserRepository.getAuthenticatedUser();
        Category category = categoryService.findById(id);
        userService.addCategory(user, category);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove-category/{id}")
    public ResponseEntity<?> removeCategory(@PathVariable Integer id) {
        User user = loggedUserRepository.getAuthenticatedUser();
        Category category = categoryService.findById(id);
        userService.removeCategory(user, category);
        return ResponseEntity.ok().build();
    }
}
