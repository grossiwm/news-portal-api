package com.newsprovider.portal.controller;

import com.newsprovider.portal.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class PublicNewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping
    public ResponseEntity<?> getNews() {
        return ResponseEntity.ok().body(newsService.getNews());
    }
}
