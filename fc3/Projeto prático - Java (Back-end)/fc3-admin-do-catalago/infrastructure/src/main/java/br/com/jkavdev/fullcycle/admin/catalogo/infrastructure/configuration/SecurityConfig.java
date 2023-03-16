package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    public static final String ROLE_ADMIN = "CATALAGO_ADMIN";
    public static final String ROLE_VIDEOS = "CATALAGO_VIDEOS";
    public static final String ROLE_GENRES = "CATALAGO_GENRES";
    public static final String ROLE_CATEGORIES = "CATALAGO_CATEGORIES";
    public static final String ROLE_CAST_MEMBERS = "CATALAGO_CAST_MEMBERS";

    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> {
                    csrf.disable();
                })
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .antMatchers("/cast_members*").hasAnyRole(ROLE_ADMIN, ROLE_CAST_MEMBERS)
                            .antMatchers("/categories*").hasAnyRole(ROLE_ADMIN, ROLE_CATEGORIES)
                            .antMatchers("/genres*").hasAnyRole(ROLE_ADMIN, ROLE_GENRES)
                            .antMatchers("/videos*").hasAnyRole(ROLE_ADMIN, ROLE_VIDEOS)
                            .anyRequest().hasAnyRole(ROLE_ADMIN);
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .headers(headers -> {
                    headers.frameOptions().sameOrigin();
                })
                .build();
    }
}
