package com.sena.jwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.jwt.model.authResponse;
import com.sena.jwt.model.loginRequest;
import com.sena.jwt.model.registerRequest;
import com.sena.jwt.service.authService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/public/user/")
public class controllerUserPublic {

    private final authService authService;

    @PostMapping("login/")
    public ResponseEntity<authResponse> login(@RequestBody loginRequest request) {
        authResponse response=authService.login(request);
        return new ResponseEntity<authResponse> (response,HttpStatus.OK);
    }

    @PostMapping("register/")
    public ResponseEntity<authResponse> register(@RequestBody registerRequest request) {
        authResponse response=authService.register(request);
        return new ResponseEntity<authResponse> (response,HttpStatus.OK);
    }

}
