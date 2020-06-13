package com.bikeshop.demo.security;

import com.bikeshop.demo.model.User;
import com.bikeshop.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


    @Service
    public class UserPrincipalDetailsService implements UserDetailsService {

        private UserRepository userRepository;

        @Autowired
        public UserPrincipalDetailsService(UserRepository userRepository) {
            this.userRepository = userRepository;

        }

        @Bean
        public Authentication getAuth() {
            return SecurityContextHolder.getContext().getAuthentication();
        }

        @Override
        public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
            User user = userRepository.findByUsername(s);
            return new UserPrincipal(user);
        }

    }