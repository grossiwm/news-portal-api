package com.newsprovider.portal.filter;

import com.newsprovider.portal.exception.UserNotAuthenticatedException;
import com.newsprovider.portal.exception.UserNotFoundException;
import com.newsprovider.portal.model.User;
import com.newsprovider.portal.repository.LoggedUserRepository;
import com.newsprovider.portal.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SubscriptionFilter extends OncePerRequestFilter {

    @Autowired
    private LoggedUserRepository loggedUserRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/premium-news")) {
            try {
                User user = loggedUserRepository.getAuthenticatedUser();
                if (user.getSubscriptions().stream()
                        .noneMatch(s -> subscriptionService.isValid(s))) {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    return;
                }
            } catch (UserNotAuthenticatedException | UserNotFoundException e) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return;
            }
        }

        filterChain.doFilter(request, response);


    }
}
