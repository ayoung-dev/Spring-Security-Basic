package org.example.jwt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsFilter corsFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //세션 사용 안 함
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class) //@CrossOrigin(인증X), SecurityFilter 등록(인증O)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/user/**").authenticated()     // /user라는 url로 들어오면 인증 필요
                        .requestMatchers("/api/v1/manager/**").hasAnyRole("ADMIN", "MANAGER")  // manager으로 들어오는 MANAGER 인증 또는 ADMIN인증 필요
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")  //admin으로 들어오면 ADMIN권한이 있는 사람만 들어올 수 있음
                        .anyRequest().permitAll()   //그리고 나머지 url은 전부 권한을 허용해준다.
                )
                .formLogin(form -> form.disable());

        return http.build();
    }
}