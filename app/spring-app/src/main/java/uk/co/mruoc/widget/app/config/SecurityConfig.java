package uk.co.mruoc.widget.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.antMatchers(getPermitAllPatterns())
                        .permitAll()
                        .antMatchers(getAuthenticatedPatterns())
                        .authenticated())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(new JwtAuthenticationConverter());
        return http.build();
    }

    private static String[] getPermitAllPatterns() {
        return new String[] {"/", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**"};
    }

    private static String[] getAuthenticatedPatterns() {
        return new String[] {"/v1/**"};
    }
}
