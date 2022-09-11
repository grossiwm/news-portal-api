package com.newsprovider.portal.service;

import com.newsprovider.portal.exception.NewsGatheringException;
import com.newsprovider.portal.model.Category;
import com.newsprovider.portal.model.User;
import com.newsprovider.portal.repository.LoggedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.stream.Collectors;

@Service
public class NewsService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoggedUserRepository loggedUserRepository;

    public Object getNews(int page) {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUri(URI.create("/news"));
        URI uri = uriComponentsBuilder.build().toUri();
        uriComponentsBuilder.queryParam("page", page);

        try {
            return restTemplate.getForObject(uri.toString(), Object.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NewsGatheringException();
        }

    }


    public Object getPremiumNews(int page) {

        User loggedUser = loggedUserRepository.getAuthenticatedUser();
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUri(URI.create("/news"));
        uriComponentsBuilder.queryParam("language", loggedUser.getNativeLanguage().getInitials());
        String categoriesParam = loggedUser.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.joining(","));

        uriComponentsBuilder.queryParam("category", categoriesParam);
        uriComponentsBuilder.queryParam("page", page);
        URI uri = uriComponentsBuilder.build().toUri();
        try {
            return restTemplate.getForObject(uri.toString(), Object.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NewsGatheringException();
        }
    }

}
