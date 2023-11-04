package com.jwt.demo.config;

import com.jwt.demo.security.CustomUserDetailsService;
import com.jwt.demo.security.JwtAuthentationEntryPoint;
import com.jwt.demo.security.JwtAuthentationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    public static final String[] URL = {
            "/api/v1/auth/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    @Autowired
    private CustomUserDetailsService customUserDetailService;

    @Autowired
    private JwtAuthentationEntryPoint jwtAuthentationEntryPoint;

    @Autowired
    private JwtAuthentationFilter jwtAuthentationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf(csrf-> csrf.disable())
                .cors(cors->cors.disable())
                .authorizeHttpRequests(auth-> auth.requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/userEntity/create").permitAll()
//                                .requestMatchers("/api/role/normal").hasRole("NORMAL")
//                                .requestMatchers("/api/role/admin").hasRole("ADMIN")
                                .requestMatchers("/api/role/about").permitAll()
                                .requestMatchers(URL).permitAll()
//                        .requestMatchers("/api/post/{postId}").hasRole("NORMAL")
//                        .requestMatchers("/api/user/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthentationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // take it from DB.
        httpSecurity.authenticationProvider(daoAuthenticationProvider());
        httpSecurity.addFilterAfter(jwtAuthentationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
