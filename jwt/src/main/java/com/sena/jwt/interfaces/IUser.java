package com.sena.jwt.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.jwt.model.user;
import java.util.Optional;

@Repository
public interface IUser extends JpaRepository<user, String> {

    Optional<user> findByUsername(String username);

}
