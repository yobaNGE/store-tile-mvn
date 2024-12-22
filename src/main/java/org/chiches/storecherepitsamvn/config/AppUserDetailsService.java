package org.chiches.storecherepitsamvn.config;

import org.chiches.storecherepitsamvn.entity.UserEntity;
import org.chiches.storecherepitsamvn.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUsername(name);
        if (userEntity.isEmpty())
            throw new UsernameNotFoundException("Username not found: " + name);

        return new User(
                userEntity.get().getUsername(),
                userEntity.get().getPassword(),
                userEntity.get().getAuthorities()
        );
    }
}