package com.itainplace.springnblog.service;

import com.itainplace.springnblog.dto.RegisterRequest;
import com.itainplace.springnblog.entities.User;
import com.itainplace.springnblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setPassword(encodePassord(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        userRepository.save(user);

    }

    private String encodePassord(String password) {
        return passwordEncoder.encode(password);
    }


}
