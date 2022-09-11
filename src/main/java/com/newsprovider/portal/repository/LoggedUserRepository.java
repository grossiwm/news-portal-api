package com.newsprovider.portal.repository;

import com.newsprovider.portal.exception.UserNotAuthenticatedException;
import com.newsprovider.portal.exception.UserNotFoundException;
import com.newsprovider.portal.model.User;
import com.newsprovider.portal.security.facade.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoggedUserRepository {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private UserRepository userRepository;

    public User getAuthenticatedUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal().equals("anonymousUser"))
            throw new UserNotAuthenticatedException();

        String email = (String) authenticationFacade.getAuthentication().getPrincipal();
        User user =  userRepository.findByEmail(email);
        if (user == null)
            throw new UserNotFoundException();
        return user;
    }
}
