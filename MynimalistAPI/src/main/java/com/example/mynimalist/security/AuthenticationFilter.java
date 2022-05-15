package com.example.mynimalist.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.mynimalist.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

        String secret = "Secret_Key";

        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        Date expiration = new Date(System.currentTimeMillis() + 60 * 60 * 1000); // El token expira en una hora
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiration)
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

       /* String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiration)
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);*/

        //response.setHeader("access_token", token);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        //tokens.put("refresh_token", refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}