package com.example.apisecuritytienda.securityConfig;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.apisecuritytienda.securityConfig.UserPermission.*;

public enum UserRole {

    //LISTADO DE ROLES
    ADMIN(Set.of( USER_WRITE, USER_READ, PRODUCTO_WRITE, PRODUCTO_READ)),
    //Su parametro es un set con un conjunto de permisos especificamente para ADMIN
    CLIENTE(Set.of(USER_WRITE, USER_READ, PRODUCTO_READ)),
    SELLER(Set.of(USER_READ, USER_WRITE, PRODUCTO_READ, PRODUCTO_WRITE));


    private final  Set<UserPermission> permissions; // los permisos son de mi enum que cree UserPermission
    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() { return permissions;}

    public String getRole(){ return this.name();}

    public Set<SimpleGrantedAuthority> getGrantedAuthority(){
        Set<SimpleGrantedAuthority> permisos = getPermissions().stream()
                .map(permissions-> new SimpleGrantedAuthority(permissions.getPermission()))
                .collect(Collectors.toSet());

        permisos.add(new SimpleGrantedAuthority("ROLE_"+ this.name()));
        return permisos;
    }
}
