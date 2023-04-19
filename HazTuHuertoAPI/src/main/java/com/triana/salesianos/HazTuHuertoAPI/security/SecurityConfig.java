package com.triana.salesianos.HazTuHuertoAPI.security;

import com.triana.salesianos.HazTuHuertoAPI.security.jwt.access.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final AccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);


        // Versión 1
        /*
        AuthenticationManager authenticationManager =
                authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                        .and().build();
        */

        // Versión 2
        AuthenticationManager authenticationManager =
            authenticationManagerBuilder.authenticationProvider(authenticationProvider())
                    .build();

        return authenticationManager;

    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setHideUserNotFoundExceptions(false);

        return authenticationProvider;

    }





    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults())
                .csrf().disable()
                        .exceptionHandling()
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                        .and()
                                .sessionManagement()
                                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                                .authorizeRequests()
                                .antMatchers("/question/**").hasAnyRole("USER", "ADMIN")
                                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                                .antMatchers("/answer/**").hasAnyRole("USER", "ADMIN")
                                .antMatchers("/vegetableGarden/**").hasAnyRole("USER", "ADMIN")
                                .antMatchers("/patch/**").hasAnyRole("USER", "ADMIN")
                                .antMatchers("/cultivation/**").hasAnyRole("USER", "ADMIN")
                                .antMatchers("/note/**").hasAnyRole("USER", "ADMIN")
                                .antMatchers("/auth/register/admin").hasRole("ADMIN")
                                .antMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated();



        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        http.headers().frameOptions().disable();

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().antMatchers("/h2-console/**", "/auth/register", "/auth/login",
                "/swagger-ui-hazTuHuerto.html", "/swagger-ui/**", "/api-docs/**"));
    }


}
