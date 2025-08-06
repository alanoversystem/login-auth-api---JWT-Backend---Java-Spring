package com.oversystem.login_auth_api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.oversystem.login_auth_api.domain.user.User;
import com.oversystem.login_auth_api.repositories.UserRepository;


public class CustomUserDetailsService implements UserDetailsService {
   
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
       return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getAuthorities());

        
       
    }


}
