package com.newsprovider.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class NewsService {

    @Autowired
    private RestTemplate restTemplate;

    public Object getNews() {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUri(URI.create("/news"));
        URI uri = uriComponentsBuilder.build().toUri();

        return restTemplate.getForObject(uri.toString(), Object.class);
    }

}
