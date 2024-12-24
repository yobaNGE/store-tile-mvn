package org.chiches.storecherepitsamvn.config;

import org.chiches.storecherepitsamvn.repository.UserRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
public class AppSecurityConfiguration {

    private final UserRepository userRepository;
    private final AuthenticationSuccessHandler successHandler;

    public AppSecurityConfiguration(UserRepository userRepository, AuthenticationSuccessHandler successHandler) {
        this.userRepository = userRepository;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SecurityContextRepository securityContextRepository) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/favicon.ico").permitAll()

                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()

                        .requestMatchers(
                                "/",
                                "/auth/login",
                                "/auth/login-error",
                                "/auth/register",
                                "/css/**",
                                "/js/**"
                        ).permitAll()

                        .requestMatchers("/orders/admin/**").hasRole("ADMIN")

                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .requestMatchers("/tiles/create", "/tiles/update/**").hasAnyRole("STAFF", "ADMIN")

                        .requestMatchers("/tile-categories/**").hasAnyRole("STAFF", "ADMIN")

                        .requestMatchers("/cart/**").hasAnyRole("USER", "STAFF", "ADMIN")

                        .requestMatchers("/catalogue/**").hasAnyRole("USER", "STAFF", "ADMIN")

                        .requestMatchers("/orders").hasAnyRole("USER", "STAFF", "ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(
                        (formLogin) ->
                                formLogin.
                                        loginPage("/auth/login").
                                        usernameParameter("username").
                                        passwordParameter("password").
                                        successHandler(successHandler).
                                        failureForwardUrl("/auth/login-error")
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                )
                .securityContext(
                        securityContext -> securityContext.
                                securityContextRepository(securityContextRepository)
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new AppUserDetailsService(userRepository);
    }
}