package com.chatlet.chatlet.config;

import com.chatlet.chatlet.services.ChatletUserDetailsService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import java.util.List;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private RsaKeyProperties rsaKeyProperties;

    private ChatletUserDetailsService chatletUserDetailsService;
    private CustomJwtAuthorizationConverter customJwtAuthorizationConverter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception {

        http.cors(c -> c.configurationSource(request -> {
            var cors = new org.springframework.web.cors.CorsConfiguration();
            cors.setAllowedOrigins(List.of("http://localhost:3000"));
            cors.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"));
            cors.setAllowCredentials(true);
            cors.setAllowedHeaders(List.of("*"));
            return cors;
        }));
        http.csrf(AbstractHttpConfigurer::disable);


        http.httpBasic(Customizer.withDefaults());
        http.authorizeHttpRequests(c -> c.requestMatchers("/register","/v3/**", "/swagger-ui/**","/topic/**","/ws/message").permitAll().anyRequest().authenticated());

        http.oauth2ResourceServer((c)-> c.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(customJwtAuthorizationConverter)));
        http.userDetailsService(chatletUserDetailsService);


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }





    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.publicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeyProperties.publicKey()).privateKey(rsaKeyProperties.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }


}
