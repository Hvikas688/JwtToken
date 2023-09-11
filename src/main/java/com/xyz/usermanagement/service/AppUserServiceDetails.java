package com.xyz.usermanagement.service;

import com.xyz.usermanagement.domain.RegisteredUser;
import com.xyz.usermanagement.repository.RegisteredUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AppUserServiceDetails implements UserDetailsService {
    private RegisteredUserRepo registeredUserRepo;

    /**
     * get userDetails object from database
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RegisteredUser registeredUser=registeredUserRepo.findByEmail(username).get();
        return new org.springframework.security.core.userdetails.User(registeredUser.getEmail(), registeredUser.getPassword(),getAuthority());

    }

    /**
     * Get the Role of user
     * @return
     */
    private Set<SimpleGrantedAuthority> getAuthority() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        return authorities;
    }
}
