package com.itainplace.springnblog.service;

import com.itainplace.springnblog.dto.LoginRequest;
import com.itainplace.springnblog.dto.RegisterRequest;
import com.itainplace.springnblog.dto.RegisterResponse;
import com.itainplace.springnblog.dto.TokenResponse;
import com.itainplace.springnblog.entities.User;
import com.itainplace.springnblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public RegisterResponse signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        userRepository.save(user);

        return new RegisterResponse(user);

    }

    private String encodePassord(String password) {
        return passwordEncoder.encode(password);
    }


    public TokenResponse login (LoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));


        var user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));


        var jwt = jwtService.generateToken(user);

        return new TokenResponse(jwt);

    }
}
