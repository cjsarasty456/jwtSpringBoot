package com.modelo.seguridad.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modelo.seguridad.model.Permission_roles;

public interface Ipermission_role extends JpaRepository<Permission_roles, Integer> {

}
