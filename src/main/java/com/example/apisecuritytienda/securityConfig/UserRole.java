package com.example.apisecuritytienda.securityConfig;

import java.util.Set;

import static com.example.apisecuritytienda.securityConfig.UserPermission.*;

public enum UserRole {

    //LISTADO DE ROLES
    ADMIN(Set.of( USER_WRITE, USER_READ, PRODUCTO_WRITE, PRODUCTO_READ)),
    //Su parametro es un set con un conjunto de permisos especificamente para ADMIN

    CLIENTE(Set.of(USER_WRITE, USER_READ, PRODUCTO_READ));


    private final  Set<UserPermission> permissions; // los permisos son de mi enum que cree UserPermission
    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }
}
