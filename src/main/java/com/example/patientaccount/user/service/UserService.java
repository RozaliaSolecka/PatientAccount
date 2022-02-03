package com.example.patientaccount.user.service;

import com.example.patientaccount.user.entity.User;
import com.example.patientaccount.user.repository.UserRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for all business actions regarding character entity.
 */
@ApplicationScoped
@NoArgsConstructor//Empty constructor is required for creating proxy while CDI injection.
public class UserService {

    private UserRepository repository;

    @Inject
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> find(Long personalUserId) {
        return repository.find(personalUserId);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void create(User user) {
        repository.create(user);
    }

    public void update(User user) {
        repository.update(user);
    }
}