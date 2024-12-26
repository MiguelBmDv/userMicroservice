package com.reto.usuario_microservice.infrastructure.output.jpa.adapter.security;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reto.usuario_microservice.domain.constant.RolConstants;
import com.reto.usuario_microservice.infrastructure.output.jpa.entity.UserEntity;
import com.reto.usuario_microservice.infrastructure.output.jpa.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

    private static final Map<Long, String> ROLE_MAP = Map.of(
            RolConstants.ADMIN_ROL_ID, "ADMIN",
            RolConstants.OWNER_ROL_ID, "OWNER",
            RolConstants.EMPLOYEE_ROL_ID, "EMPLOYEE",
            RolConstants.USER_ROL_ID, "USER"
    );

    private static final String DEFAULT_ROLE = "USER";

    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

                return new CustomUserDetails(
                    user.getEmail(),
                    user.getPassword(),
                    user.getDocumentNumber(), 
                    user.getFirstName(),
                    getAuthorities(user.getRolId())
            );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Long rolId) {
        // Resuelve el rol usando el mapa o asigna el rol predeterminado
        String roleName = ROLE_MAP.getOrDefault(rolId, DEFAULT_ROLE);
        return List.of(new SimpleGrantedAuthority(roleName));
    }

}
