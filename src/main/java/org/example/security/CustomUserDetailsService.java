package org.example.security;

import org.example.entity.AuthUser;
import org.example.repository.AuthUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserDao authUserDao;

    @Autowired
    public CustomUserDetailsService(AuthUserDao authUserDao) {
        this.authUserDao = authUserDao;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserDao.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with username " + username));
        return new User(authUser.getUsername(), authUser.getPassword(), List.of());
    }

}
