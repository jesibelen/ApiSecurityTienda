package com.example.apisecuritytienda.securityConfig.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUserEntity, Integer>{
    AppUserEntity findAppUserEntityByUsername(String username);
}
