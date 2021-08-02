package com.example.apisecuritytienda.securityConfig;

import com.example.apisecuritytienda.securityConfig.auth.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
    # Si no realizo la configuracion de Security, igualmente se me autogenera una contrasenia dada por consola
 y mi usuario sera user, la otra opcion seria si no queremos una contrasnia autogenerada, para probar no mas...
 Seria escribir en aplication.properties lo siguiente:

    spring.security.user.name= jesi
    spring.security.user.password = miclave

*/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfigWeb extends WebSecurityConfigurerAdapter {

    //Se inyecta el appUserService
    private final AppUserService appUserService;
    @Autowired
    public SecurityConfigWeb(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(appUserService);
        return provider;
    }
}
