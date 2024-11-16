package com.chatlet.chatlet.interceptors;

import com.chatlet.chatlet.services.ChatletUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@AllArgsConstructor
@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    private JwtDecoder jwtDecoder;
    private ChatletUserDetailsService chatletUserDetailsService;


    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {


        String token = extractTokenFromQuery(request.getURI());

        if (token != null ) {

            Jwt jwt = null;
            try {
                jwt = jwtDecoder.decode(token);
            } catch (JwtException e) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return false;
            }
            String username = jwt.getSubject();
            // Load user details and set security context
            UserDetails userDetails = chatletUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            attributes.put("username", username);
            attributes.put("SECURITY_CONTEXT_WEBSOCKET", SecurityContextHolder.getContext());
            return true;
        } else {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }

    private String extractTokenFromQuery(URI uri) {
        // Extract token from query parameter "token"
        return UriComponentsBuilder.fromUri(uri)
                .build()
                .getQueryParams()
                .getFirst("token");
    }



}
