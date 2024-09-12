package com.example.scofs;

import com.example.loginsystem.JwtTokenFilter;
import com.example.loginsystem.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig{


    private JwtTokenProvider jwtTokenProvider;

    private JwtTokenFilter jwtTokenFilter;



//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .dispatcherTypeMatchers("/api/auth/**").permitAll()
//                .anyRequest().authenticated();
//        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Disable CSRF for now
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Allow all requests under /api/auth
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before username-password filter

        return http.build();
    }
}