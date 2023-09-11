package com.xyz.usermanagement.config;


import com.xyz.usermanagement.sercurity.JwtSecurityFilter;
import com.xyz.usermanagement.sercurity.SecurityEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableMethodSecurity
public class SpringSecurityConfig {
    @Autowired
    private SecurityEntryPoint point;
    @Autowired
    private JwtSecurityFilter filter;

    /**
     * configure http method with jwt interrupter
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
//                        .requestMatchers("/api/**").authenticated()  // secure
                        .requestMatchers("/api/v1/**").permitAll()) //allow all
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
