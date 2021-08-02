package com.example.apisecuritytienda.securityConfig.auth;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

/* # IAM = identy and access management es una rama muy abarcativa en seguridad.
    IAM trata sobre Gestion de acceso (o sea log in|log out /autotenticacion / autorizacion)
    y Gestion de Identidades (Crear usuarios)
    ----------------------------------------------------------------------------
    # IM = Identity managment (se encarga en como dar permisos o crear un rol para alguien,
    como borro o desactivo usuarios si ya no pertenece a la compañia)

    Los developers se encargan solo en gestion de accesos (tambien el equipo de seguridad) ya que la gestion de identidades
    esta mas relacionados con RRHH, la parte de negocio (CLIENTES), y Seguridad.

    LDAP/ AD (Active Directory)

    private Collection<? extends  GrantedAuthority> authorities;
    Lo que voy a guardar en authorities es una coleccion <? (de cosas que no se que son)
    que entiende los mismos mensajes que la interfaz de GrantedAuthority (AutorizacionesConcedidas)
    */

    /* Es una Anotacion de JPA que me indica que tengo algo complejo y
    que el Set<...> es una coleccion de elementos complejos*/
// ## @ElementCollection(fetch = FetchType.EAGER, targetClass = ArrayList.class)
    /*EAGER para que SIEMPRE me traiga los permisos |
    En targetClass indico que clase concreta va a mapear en este caso
    un ArrayList.class donde quiero que instancie esos objetos
    El targetClass no es fundamental ya que se puede no utilizarlo porque igualmente spring y hibernate puede decidir que clase concreta de modo default*/
// ## @Convert(converter = SimpleGrantedAuthorityConverter.class)
// Nos permite guardar en la base de datos una cosa y convertirlo en otra cosa dentro de Java (ver la clase 27/07 0:54)
//private Set< SimpleGrantedAuthority> grantedAuthorities;



@Entity
@Table(name="users")
public class AppUserEntity implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;
    @Column(unique = true) // hace que el username sea unico
    private String username;
    private String password;

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER) //Aqui es EAGER porque es necesario saber el rol que tiene los users
    @JoinTable(
            name = "users_x_authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    /*@Basic : indica que lo que tenes no es una clase personalizada o
    complejo sino algo basico (boolean,integer), o sea tratalo como
    un DATO PRIMITIVO*/

    private Collection<MySimpleGrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialNonExpired;
    private boolean enabled;

    public AppUserEntity(Integer user_id, String username, String password, Collection<MySimpleGrantedAuthority> authorities, boolean accountNonExpired, boolean accountNonLocked, boolean credentialNonExpired, boolean enabled) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this .authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialNonExpired = credentialNonExpired;
        this.enabled = enabled;
    }

    public AppUserEntity() {
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<MySimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialNonExpired;
    }

    public void setCredentialNonExpired(boolean credentialNonExpired) {
        this.credentialNonExpired = credentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "AppUserEntity{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialNonExpired=" + credentialNonExpired +
                ", enabled=" + enabled +
                '}';
    }
}
