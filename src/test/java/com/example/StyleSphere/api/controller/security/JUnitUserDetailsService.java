package com.example.StyleSphere.api.controller.security;

import com.example.StyleSphere.models.repository.UserRepository;
import com.example.StyleSphere.models.LocalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service to provide spring with Authentication Principals for unit testing.
 */
@Service
@Primary
public class JUnitUserDetailsService implements UserDetailsService {

    /** The Local User DAO. */
    @Autowired
    private UserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LocalUser> opUser = userRepository.findByUsernameIgnoreCase(username);
        if (opUser.isPresent())
            return opUser.get();
        return null;
    }

}
