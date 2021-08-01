package com.example.apisecuritytienda.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

import static com.example.apisecuritytienda.securityConfig.UserPermission.PRODUCTO_READ;
import static com.example.apisecuritytienda.securityConfig.UserPermission.PRODUCTO_WRITE;
import static com.example.apisecuritytienda.securityConfig.UserRole.ADMIN;
import static com.example.apisecuritytienda.securityConfig.UserRole.CLIENTE;

/*
    # Si no realizo la configuracion de Security, igualmente se me autogenera una contrasenia dada por consola
 y mi usuario sera user, la otra opcion seria si no queremos una contrasnia autogenerada, para probar no mas...
 Seria escribir en aplication.properties lo siguiente:

    spring.security.user.name= jesi
    spring.security.user.password = miclave

*/
@Configuration
@EnableWebSecurity //activa el metodo configure de HttpSecurity
public class SecurityConfigWeb extends WebSecurityConfigurerAdapter {

    @Override
    //configura la seguridad, filtros...
    protected void configure(HttpSecurity http) throws Exception {
        /*El orden de la configuracion y  de los filtros se chequea de arriba hacia abajo,
        el que se encuentre arriba prevalece o predomina mas */

        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/","/index.html").permitAll()
            //.antMatchers("/api/productos/**").permitAll()//No hacer este sacrilegio porque sino tirare all a la basura mis siguientes filtros :(
                //solo lo que tengan permisos de escritura pueden hacer PUT en la url /api/productos/**
            .antMatchers(HttpMethod.PUT,"/api/productos/**").hasAuthority(PRODUCTO_WRITE.getPermission())
            .antMatchers(HttpMethod.POST,"/api/**").hasAuthority(PRODUCTO_WRITE.getPermission()) //
                //El metodo delate sobre productos pueden hacerlo solamente los que tengan rol de ADMIN
            .antMatchers(HttpMethod.DELETE,"/api/productos/**").hasRole(ADMIN.getRole()) //Este se puede tanto con jesi y bart
            //.antMatchers(HttpMethod.DELETE,"/api/productos/**").hasAuthority(PRODUCTO_WRITE.getPermission())// Este no funciona con jesi
                //hasAnyRole(ADMIN.getRole(), CLIENTE.getRole())
            .antMatchers(HttpMethod.GET,"/api/productos/**").permitAll()
            //.antMatchers("/api/productos/**").hasAuthority(PRODUCTO_READ.getPermission())// este no me permite hacer GET con jesi ya que no le asigne cuales son sus permisos, solo su rol con .roles
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic();


    }

    @Bean
    public PasswordEncoder passwordEncoder(){//paswordEnconder es un obj que pedimos a spring que nos intancie
        return new BCryptPasswordEncoder(5); //Bcrypt es un algoritmo que hashea nuestra pwd
        //el parametro strenght me indica la cantidad de veces que se hasheara mi clave mediante el algoritmo BCrypt
    }


    //Generator -> @OverrrideMethods -> UserDetailsService():UserDetailsService
    @Override
    @Bean //Instanciame este objeto (interfaz) UserDetailService no me autogeneres la contrasenia por consola
    protected UserDetailsService userDetailsService() {
        UserDetails usuario1 = User.builder()
                .username("jesi")
                .password(passwordEncoder().encode("clave"))
                .roles(UserRole.ADMIN.getRole())
                //.authorities(ADMIN.getGrantedAuthorities())
                .build();
/*
    .authorities(ADMIN.getGrantedAuthorities())
    .roles(UserRole.ADMIN.name())
    Nota: Con el postman mediante el request mi-rol se puede observar que roles() pisa todas mis filtros que hice con authorities
    ------------------------------------------------------------------------------------------------------------------------
    SimpleGrantedAuthority es una Clase que implementa la interfaz GrantedAthority
 */
// El name me devuelve el nombre ADMIN en string
                // , parecido lo que hicimos anteriormente solo que es nuestro ADMIN que generamos nosotros y no el de Spring
                //.roles("ADMIN")// en el caso sin ENUM se crearia por spring automaticamente como ROLE_ADMIN

        UserDetails usuario2 = User.builder()
                .username("milhouse")
                .password(passwordEncoder().encode("hola"))
                .authorities(UserRole.CLIENTE.getGrantedAuthorities()) // tambien lo mismo se crearia ROLE_CLIENTE
                .build();

        UserDetails usuario3 = User.builder()
                .username("bart")
                .password(passwordEncoder().encode("hola"))
                .authorities(ADMIN.getGrantedAuthorities())// se relaciona con mi metodo 1 de UserRole
                .build();

        //Terces caso
        UserDetails usuario4 = User.builder()
                .username("tester")
                .password(passwordEncoder().encode("clave"))
                .authorities(UserRole.SELLER.getGrantedAuthorities())
                .build();
        return new InMemoryUserDetailsManager(usuario1,usuario2,usuario3,usuario4);


     //   Un rol es una lista de autoridades (permisos) que son de la clase SimpleGrantedAuthority (La autoridad concedidad)
        /*
        El userdetails tiene atributos cmo set de autoridades (No una lista porque no se tiene que repetir los permisos)
        tambien tiene un pwd, username, y 4 booleanos (cuentaNoExpirada, cuentaNoBloqueada, credencialNoExpirada)
         */
    }
}
