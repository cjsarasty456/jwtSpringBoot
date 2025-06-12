package com.modelo.seguridad.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modelo.seguridad.model.Roles;

public interface Irole extends JpaRepository<Roles, Integer> {

}
