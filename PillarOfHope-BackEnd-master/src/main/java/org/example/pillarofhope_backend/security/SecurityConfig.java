package org.example.pillarofhope_backend.security;

import lombok.AllArgsConstructor;
import org.example.pillarofhope_backend.service.CustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final CustomAuthenticationManager customAuthenticationManager;
    private CustomerService customerService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        AuthenticationFilter authenticationFilter= new AuthenticationFilter(customAuthenticationManager,customerService);
        authenticationFilter.setFilterProcessesUrl("/authenticate");
        httpSecurity
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(request -> {
                            var cors=new CorsConfiguration();
                            cors.setAllowedOrigins(List.of("*"));
                            cors.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
                            cors.setAllowedHeaders(List.of("*"));
                            cors.setExposedHeaders(List.of("*"));
                            return cors;
                        }
                        ))
                .headers(headers-> headers.frameOptions(frameOptions->frameOptions.disable()))
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .authorizeHttpRequests(authorize-> authorize
                        .requestMatchers(HttpMethod.POST,"/api/customers/register").permitAll()
                        .requestMatchers(HttpMethod.POST,"api/customers/register-admin").permitAll()
                        .requestMatchers(HttpMethod.PUT,"api/customers/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST,"api/medicine/upload").permitAll()
                        .requestMatchers(HttpMethod.GET,"api/medicine/").permitAll()
                        .requestMatchers(HttpMethod.GET,"api/medicine/{medicineName}").permitAll()
                        .requestMatchers(HttpMethod.PUT,"api/medicine/{id}").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"api/medicine/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET,"api/donations/").permitAll()
                        .requestMatchers(HttpMethod.GET,"api/donations/gold").permitAll()
                        .requestMatchers(HttpMethod.GET,"api/donations/silver").permitAll()
                        .requestMatchers(HttpMethod.GET,"api/donations/bronze").permitAll()
                        .anyRequest().authenticated()

                )
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JwtVerificationFilter(customerService),AuthenticationFilter.class)
                .sessionManagement(sessionManagement->sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return httpSecurity.build();
    }
}
