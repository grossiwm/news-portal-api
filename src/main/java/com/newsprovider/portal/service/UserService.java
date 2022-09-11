package com.newsprovider.portal.service;

import com.newsprovider.portal.exception.UserNotFoundException;
import com.newsprovider.portal.exception.UsernameAlreadyTakenException;
import com.newsprovider.portal.model.User;
import com.newsprovider.portal.model.enums.PaymentStatus;
import com.newsprovider.portal.repository.UserRepository;
import com.newsprovider.portal.security.MyUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private PaymentService paymentService;

    public void save(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException die) {
            throw new UsernameAlreadyTakenException();
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null)
            throw new UsernameNotFoundException(username);

        return new MyUserPrincipal(user);
    }

    public boolean hasActiveSubscription(User user) {
        return user.getSubscriptions().stream()
                .anyMatch(s -> subscriptionService.isValid(s));
    }

    public boolean hasPendingPayments(User user) {
        return !paymentService.findByPaymentStatusAndUser(PaymentStatus.REQUESTED, user).isEmpty();
    }

}
