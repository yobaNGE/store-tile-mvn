package org.chiches.storecherepitsamvn.service.impl;

import org.chiches.storecherepitsacontracs.dto.user.UserForm;
import org.chiches.storecherepitsamvn.entity.RoleEntity;
import org.chiches.storecherepitsamvn.entity.UserEntity;
import org.chiches.storecherepitsamvn.entity.consts.Roles;
import org.chiches.storecherepitsamvn.repository.RoleRepository;
import org.chiches.storecherepitsamvn.repository.UserRepository;
import org.chiches.storecherepitsamvn.service.AuthService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UserForm form) {
        if (userRepository.findByUsername(form.username()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        UserEntity user = new UserEntity();

        user.setUsername(form.username());
        user.setEmail(form.email());
        user.setPassword(passwordEncoder.encode(form.password()));
        user.setName(form.name());
        user.setSurname(form.surname());

        RoleEntity userRole = roleRepository.findByRole(Roles.USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(List.of(userRole));

        userRepository.save(user);
    }
}