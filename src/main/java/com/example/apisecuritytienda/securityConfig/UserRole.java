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

    SELLER(Set.of(USER_READ, USER_WRITE, PRODUCTO_READ));

//-----------------------------------------------------------------------
    // mi set de permissions son de mi enum que cree en UserPermission
    private final  Set<UserPermission> permissions;
    //en el constructor de UserRole le envio como parametro mi Set de permisos
    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }
//-----------------------------------------------------------------------
    public Set<UserPermission> getPermissions() {
        return permissions;
    }

/*
El metodo getPermisosConcedidos retorna un set de objetos de clase SimpleGrantedAutorithy

 */
    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){ //(metodo 1 obtener autoridares concedidas o permisos)
        Set<SimpleGrantedAuthority> permisos =
                getPermissions().stream()
                .map(permissions-> new SimpleGrantedAuthority(permissions.getPermission()))
                .collect(Collectors.toSet());
        // el mapa va agarra cada uno de mis permisos (ej:USER_WRITE) lo va a convertir en un objeto de SimpleGrantedAuthority con su respectivo string (ej: "user:write")

        permisos.add(new SimpleGrantedAuthority("ROLE_"+ this.name()));//Luego agrego ROLE_ y su nombre de rol.
        return permisos;
    }

    public String getRole() {
        return this.name();
    }
}
