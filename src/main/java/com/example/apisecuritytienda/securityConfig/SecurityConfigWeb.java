package com.example.apisecuritytienda.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //para habilitar el (Antes)@preAuthorize o (Despues)@PostAuthorize
public class SecurityConfigWeb extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/","/index.html").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(5);
        //el parametro strenght me indica la cantidad de veces que se hasheara mi clave mediante el algoritmo BCrypt
    }

    //Generator -> @OverrrideMethods -> UserDetailsService():UserDetailsService
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails usuario1 = User.builder()
                .username("jesi")
                .password(passwordEncoder().encode("clave"))
                .authorities(UserRole.ADMIN.getGrantedAuthority())
                .build();

        UserDetails usuario2 = User.builder()
                .username("milhouse")
                .password(passwordEncoder().encode("hola"))
                .authorities(UserRole.CLIENTE.getGrantedAuthority())
                .build();

        UserDetails usuario3 = User.builder()
                .username("bart")
                .password(passwordEncoder().encode("hola"))
                .authorities(UserRole.ADMIN.getGrantedAuthority())
                .build();

        UserDetails usuario4 = User.builder()
                .username("tester")
                .password(passwordEncoder().encode("clave"))
                .authorities(UserRole.SELLER.getGrantedAuthority())
                .build();

        return new InMemoryUserDetailsManager(usuario1,usuario2,usuario3,usuario4);

        /*
        Un rol es una lista de autoridades (permisos) que son de la clase SimpleGrantedAuthority (La autoridad concedidad)
         */
    }
}
