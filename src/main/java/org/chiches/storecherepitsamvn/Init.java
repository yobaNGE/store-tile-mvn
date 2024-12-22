package org.chiches.storecherepitsamvn;

import org.chiches.storecherepitsamvn.entity.RoleEntity;
import org.chiches.storecherepitsamvn.entity.UserEntity;
import org.chiches.storecherepitsamvn.entity.consts.Roles;
import org.chiches.storecherepitsamvn.repository.RoleRepository;
import org.chiches.storecherepitsamvn.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Init implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public Init(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        List<Roles> roles = List.of(Roles.USER, Roles.STAFF, Roles.ADMIN);
        for (Roles role : roles) {
            if (!roleRepository.existsByRole(role))
                roleRepository.save(new RoleEntity(role));
        }
        if (userRepository.findByUsername("admin").isEmpty()) {
            RoleEntity adminRole = roleRepository.findByRole(Roles.ADMIN)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            UserEntity userEntity = new UserEntity();
            userEntity.setName("admin");
            userEntity.setSurname("admin");
            userEntity.setEmail("admin@mail.ru");
            userEntity.setPassword(passwordEncoder.encode("admin"));
            userEntity.setRoles(List.of(adminRole));
            userEntity.setUsername("admin");
            userRepository.save(userEntity);
        }
        if (userRepository.findByUsername("staff").isEmpty()) {
            RoleEntity staffRole = roleRepository.findByRole(Roles.STAFF)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            UserEntity userEntity = new UserEntity();
            userEntity.setName("staff");
            userEntity.setSurname("staff");
            userEntity.setEmail("staff@mail.ru");
            userEntity.setPassword(passwordEncoder.encode("staff"));
            userEntity.setRoles(List.of(staffRole));
            userEntity.setUsername("staff");
            userRepository.save(userEntity);
        }
    }
}
