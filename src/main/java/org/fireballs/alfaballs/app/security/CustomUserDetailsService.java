package org.fireballs.alfaballs.app.security;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.fireballs.alfaballs.app.service.UserService;
import org.fireballs.alfaballs.domain.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        User appUser = userService.getUserByEmail(email);

        return org.springframework.security.core.userdetails.User.builder()
                .username(email)
                .password(appUser.getPassword())
                .authorities(new SimpleGrantedAuthority("ROLE_" + appUser.getGlobalRole().name()))
                .build();
    }
}
