package com.reto.usuario_microservice.infrastructure.output.jpa.adapter.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private final String email;
    private final String password;
    private final Long documentNumber;
    private final String name;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String email, String password, Long documentNumber, String name, Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.password = password;
        this.documentNumber = documentNumber;
        this.name = name;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getDocumentNumber() {
        return documentNumber;
    }

    public String getName() {
        return name;
    }
}
