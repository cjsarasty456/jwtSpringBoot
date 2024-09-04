package com.sena.jwt.interfaceService;

import java.util.List;
import java.util.Optional;

import com.sena.jwt.model.authResponse;
import com.sena.jwt.model.registerRequest;
import com.sena.jwt.model.user;

public interface IUserServices {

    public authResponse register(registerRequest request);

    public Optional<user> findByUsername(String username);

    public boolean delete(String id);

    public List<user> findAll();

}
