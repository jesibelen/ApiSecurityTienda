package com.example.apisecuritytienda.securityConfig.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name="authorities")
public class MySimpleGrantedAuthority implements GrantedAuthority {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authority_id;
    @Column(nullable = false) //La columna rol no tiene que ser nula
    private String role;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY )// Aqui no es necesario traer los usuarios que tiene un tipo de rol excepto si lo pido
    private Set<AppUserEntity> usuarios;

    public MySimpleGrantedAuthority(String role) {
        this.role=role;
    }

    public MySimpleGrantedAuthority() {

    }
    public String getAuthority() {
        return this.role;
    }

    public String toString() {
        return this.role;
    }

    public void setAuthority_id(Integer authority_id) {
        this.authority_id = authority_id;
    }

    public Integer getAuthority_id() {
        return authority_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<AppUserEntity> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<AppUserEntity> usuarios) {
        this.usuarios = usuarios;
    }
}
