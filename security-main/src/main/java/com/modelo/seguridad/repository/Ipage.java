package com.modelo.seguridad.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modelo.seguridad.model.Pages;

public interface Ipage extends JpaRepository<Pages, Integer> {

}
