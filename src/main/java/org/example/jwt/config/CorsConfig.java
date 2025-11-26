package org.example.jwt.config;

import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);  //내 서버가 응답할 때 json을 js에서 처리할 수 있게 할 지
        config.addAllowedOrigin("*"); //모든 ip에 응답 허용
        config.addAllowedHeader("*"); //모든 header에 응답 허용
        config.addAllowedMethod("*"); // 모든 POST, GET, PUT, DELETE, PATCH... 모든 요청 허용
        source.registerCorsConfiguration("/api/**",config);   // 해당 URL 들은 이 config 수행

        return new CorsFilter(source);
    }
}