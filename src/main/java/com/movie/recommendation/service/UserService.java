package com.movie.recommendation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.movie.recommendation.model.User;
import com.movie.recommendation.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findById(Integer id) {
        return userRepository.findById(Long.valueOf(id)).orElse(null);
    }

    // More methods as per requirement
}
