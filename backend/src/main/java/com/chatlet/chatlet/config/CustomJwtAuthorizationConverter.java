package com.chatlet.chatlet.config;

import com.chatlet.chatlet.services.ChatletUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CustomJwtAuthorizationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final ChatletUserDetailsService chatletUserDetailsService;



    @Override
    public final AbstractAuthenticationToken convert(Jwt jwt) {

        UserDetails securityUser = chatletUserDetailsService.loadUserByUsername(jwt.getClaim("sub"));
        return new UsernamePasswordAuthenticationToken(securityUser, "N/A", securityUser.getAuthorities());
    }


}
