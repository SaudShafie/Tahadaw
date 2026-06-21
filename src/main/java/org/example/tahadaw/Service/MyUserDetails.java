package org.example.tahadaw.Service;

import lombok.RequiredArgsConstructor;
import org.example.tahadaw.Api.ApiException;
import org.example.tahadaw.Model.User;
import org.example.tahadaw.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Loads a user by username for Spring Security (HTTP Basic + session, no JWT).
 */
@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new ApiException("wrong username or password");
        }
        return user;
    }
}
