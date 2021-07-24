package com.example.apisecuritytienda.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/*
    # Si no realizo la configuracion de Security, igualmente se me autogenera una contrasenia dada por consola
 y mi usuario sera user, la otra opcion seria si no queremos una contrasnia autogenerada, para probar no mas...
 Seria escribir en aplication.properties lo siguiente:

    spring.security.user.name= jesi
    spring.security.user.password = miclave

*/
@Configuration
@EnableWebSecurity
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
    @Bean //Instanciame este objeto UserDetailService no me autogeneres la contrasenia por consola
    protected UserDetailsService userDetailsService() {
        UserDetails usuario1 = User.builder()
                .username("jesi")
                .password(passwordEncoder().encode("clave"))
                .roles(UserRole.ADMIN.name()) // El name me devuelve el nombre ADMIN en string
                // , parecido lo que hicimos anteriormente solo que es nuestro ADMIN que generamos nosotros y no el de Spring
                //.roles("ADMIN")// en el caso sin ENUM se crearia por spring automaticamente como ROLE_ADMIN
                .build();

        UserDetails usuario2 = User.builder()
                .username("milhouse")
                .password(passwordEncoder().encode("hola"))
                .roles(UserRole.CLIENTE.name()) // tambien lo mismo se crearia ROLE_CLIENTE
                .build();

        UserDetails usuario3 = User.builder()
                .username("bart")
                .password(passwordEncoder().encode("hola1"))
                .roles(UserRole.ADMIN.name())
                .build();
        return new InMemoryUserDetailsManager(usuario1,usuario2,usuario3);

        /*
        Un rol es una lista de autoridades (permisos) que son de la clase SimpleGrantedAuthority (La autoridad concedidad)
         */
    }
}
