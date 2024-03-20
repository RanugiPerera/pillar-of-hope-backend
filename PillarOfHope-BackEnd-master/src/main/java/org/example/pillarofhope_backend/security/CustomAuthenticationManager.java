package org.example.pillarofhope_backend.security;

import lombok.AllArgsConstructor;
import org.example.pillarofhope_backend.entity.Customer;
import org.example.pillarofhope_backend.service.CustomerService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {
    private CustomerService customerService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Customer customer= customerService.getCustomer(authentication.getName());
        if (!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(),customer.getPassword())) {
        throw new BadCredentialsException("wrong password");
        };
        return new UsernamePasswordAuthenticationToken(authentication.getName(),customer.getPassword(),grantedAuthority(customer.getRole()));
    }
    private List<SimpleGrantedAuthority> grantedAuthority(String authority){
        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(authority));
        return authorities;
    }
}
