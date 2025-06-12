package com.modelo.seguridad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import com.modelo.seguridad.repository.Ipage;
import com.modelo.seguridad.repository.Ipermission_role;
import com.modelo.seguridad.repository.Irole;
import com.modelo.seguridad.DTO.ResponsesDTO;
import com.modelo.seguridad.model.Permission_roles;
import com.modelo.seguridad.DTO.Permission_roleDTO;
import com.modelo.seguridad.model.Pages;
import com.modelo.seguridad.model.Roles;
import java.util.List;
import java.util.Optional;


@Service
public class Permission_roleService {
    @Autowired
    private Ipermission_role data;

    @Autowired
    private Irole roleRepository;

    @Autowired
    private Ipage pageRepository;

    public List<Permission_roles> findAll() {
        return data.findAll();
    }

    public Optional<Permission_roles> findById(int id) {
        return data.findById(id);
    }

    public ResponsesDTO deletePermissionRole(int id) {
        Optional<Permission_roles> permissionRole = findById(id);
        if (!permissionRole.isPresent()) {
            return new ResponsesDTO(HttpStatus.NOT_FOUND.toString(), "El permiso de rol no existe");
        }

        data.deleteById(id);
        return new ResponsesDTO(HttpStatus.OK.toString(), "Permiso de rol eliminado correctamente");
    }

    public ResponsesDTO save(Permission_roleDTO permissionRoleDTO) {
        Permission_roles permissionRole = convertToModel(permissionRoleDTO);
        data.save(permissionRole);
        return new ResponsesDTO(HttpStatus.OK.toString(), "Permiso de rol guardado correctamente");
    }

    public ResponsesDTO updatePermissionRole(int id, Permission_roleDTO permissionRoleDTO) {
        Optional<Permission_roles> permissionRole = findById(id);
        if (!permissionRole.isPresent()) {
            return new ResponsesDTO(HttpStatus.NOT_FOUND.toString(), "El permiso de rol no existe");
        }

        Permission_roles updatedPermissionRole = permissionRole.get();
        updatedPermissionRole.setPage(permissionRoleDTO.getPage());
        updatedPermissionRole.setRole(permissionRoleDTO.getRole());
        updatedPermissionRole.setType(permissionRoleDTO.getType());

        data.save(updatedPermissionRole);
        return new ResponsesDTO(HttpStatus.OK.toString(), "Permiso de rol actualizado correctamente");
    }

    public Permission_roleDTO convertToDTO(Permission_roles permissionRole) {
        Permission_roleDTO dto = new Permission_roleDTO();
        dto.setPermission_roleid(permissionRole.getPermission_roleid());
        dto.setPage(permissionRole.getPage());
        dto.setRole(permissionRole.getRole());
        dto.setType(permissionRole.getType());
        return dto;
    }

    public Permission_roles convertToModel(Permission_roleDTO permissionRoleDTO) {
        Pages page = pageRepository.findById(permissionRoleDTO.getPage().getPageid())
                .orElseThrow(() -> new RuntimeException("Page not found"));
        Roles role = roleRepository.findById(permissionRoleDTO.getRole().getRoleid())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return new Permission_roles(
            permissionRoleDTO.getPermission_roleid(),
            page,
            role,
            permissionRoleDTO.getType()
        );
       
    }

}
