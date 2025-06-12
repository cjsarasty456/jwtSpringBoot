package com.modelo.seguridad.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.modelo.seguridad.DTO.RequestLoginDTO;
import com.modelo.seguridad.DTO.RequestRegisterUserDTO;
import com.modelo.seguridad.DTO.ResponseLogin;
import com.modelo.seguridad.DTO.ResponsesDTO;
import com.modelo.seguridad.DTO.UserDTO;
import com.modelo.seguridad.model.Roles;
import com.modelo.seguridad.model.Users;
import com.modelo.seguridad.repository.Iuser;
import com.modelo.seguridad.service.jwt.jwtServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService  {
 
    private final Iuser data;
    private final jwtServices jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public List<Users> findAll() {
        return data.findAll();
    }

    public Optional<Users> findById(int id) {
        return data.findById(id);
    }

    public Optional<Users> findByUsername(String username) {
        return data.findByUsername(username);
    }

    public Optional<Users> findByEmail(String Email) {
        return data.findByEmail(Email);
    }

    public ResponsesDTO deleteUser(int id) {
        Optional<Users> usuario = findById(id);
        if (!usuario.isPresent()) {
            return new ResponsesDTO(HttpStatus.NOT_FOUND.toString(), "El usuario no existe");
        }

        data.deleteById(id);
        return new ResponsesDTO(HttpStatus.OK.toString(), "Usuario eliminado correctamente");
    }

    

    public ResponsesDTO save(RequestRegisterUserDTO userDTO) {
        Users usuario = convertToModelRegister(userDTO);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        data.save(usuario);
        return new ResponsesDTO(HttpStatus.OK.toString(), "Usuario guardado correctamente");
    }

   
    public ResponseLogin login(RequestLoginDTO login) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername(),
                        login.getPassword()));
        UserDetails user=data.findByUsername(login.getUsername()).orElseThrow();
        ResponseLogin response=new ResponseLogin(jwtService.getToken(user));
        return response;

    }

    public ResponsesDTO updateUser(int id, UserDTO userDTO) {
        Optional<Users> usuario = findById(id);
        if (!usuario.isPresent()) {
            return new ResponsesDTO(HttpStatus.NOT_FOUND.toString(), "El usuario no existe");
        }

        Users updatedUser = usuario.get();
        updatedUser.setUsername(userDTO.getUsername());
        updatedUser.setPassword(userDTO.getPassword());
        updatedUser.setEmail(userDTO.getEmail());
        updatedUser.setEnabled(userDTO.isEnabled());
        updatedUser.setRole(userDTO.getRole());

        data.save(updatedUser);
        return new ResponsesDTO(HttpStatus.OK.toString(), "Usuario actualizado correctamente");
    }

    public Users convertToModelRegister(RequestRegisterUserDTO usuario) {
        Roles rol = new Roles();
        // asignamos rol por defecto
        // registrar el rol 1 como usuario estandar
        rol.setRoleid(1);
        return new Users(
                0,
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getEmail(),
                true,
                rol);
    }

    public Users convertToModel(UserDTO userDTO) {
        Roles rol =new Roles();
        //rol por defecto, recordar registrar en base datos este como rol default
        rol.setRoleid(1);

        return new Users(
                0,
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                userDTO.isEnabled(),
                rol);
    }

  
}
