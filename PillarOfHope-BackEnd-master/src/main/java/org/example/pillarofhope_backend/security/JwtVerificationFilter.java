package org.example.pillarofhope_backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.pillarofhope_backend.service.CustomerService;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@AllArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
    private CustomerService customerService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header=request.getHeader("Authorization");
        if (header==null || ! header.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        String token=header.replace("Bearer ","");
        String email= JWT.require(Algorithm.HMAC512(SecurityConstant.SECURITY_KEY))
                .build()
                .verify(token)
                .getSubject();
        request.setAttribute("email",email);
        Authentication authentication=new UsernamePasswordAuthenticationToken(email,null, grantedAuthority(customerService.getCustomer(email).getRole()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }
    private List<SimpleGrantedAuthority> grantedAuthority(String authority){
        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(authority));
        return authorities;
    }
}
