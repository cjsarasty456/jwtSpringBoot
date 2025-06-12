package com.modelo.seguridad.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modelo.seguridad.model.Recovery_requests;

public interface Irecovery_request extends JpaRepository<Recovery_requests, Integer> {
}
