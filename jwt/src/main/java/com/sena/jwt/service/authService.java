package com.sena.jwt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sena.jwt.interfaceService.IUserServices;
import com.sena.jwt.interfaces.IUser;
import com.sena.jwt.model.authResponse;
import com.sena.jwt.model.loginRequest;
import com.sena.jwt.model.registerRequest;
import com.sena.jwt.model.role;
import com.sena.jwt.model.user;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class authService implements IUserServices {

    private final IUser dataUser;
    private final jwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public authResponse register(registerRequest request) {
        user userData = user.builder()
                .first_name(request.getFirst_name())
                .last_name(request.getLast_name())
                .role(role.User)
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        dataUser.save(userData);
        return authResponse.builder()
                .token(jwtService.getToken(userData))
                .build();
    }

    @Override
    public Optional<user> findByUsername(String username) {
        return dataUser.findByUsername(username);
    }

    @Override
    public boolean delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<user> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    public authResponse login(loginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));
        user user = findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return authResponse
                .builder()
                .token(token)
                .build();
    }

}
