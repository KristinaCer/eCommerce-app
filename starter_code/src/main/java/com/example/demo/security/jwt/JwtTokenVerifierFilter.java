package com.example.demo.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static java.util.Arrays.stream;

public class JwtTokenVerifierFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header  = request.getHeader(SecurityConstants.HEADER_STRING_ACCESS_TOKEN);
            //request rejected
        if(header == null && !header.startsWith(SecurityConstants.TOKEN_PREFIX)){
            filterChain.doFilter(request, response);
            return;
        }
        try{
            UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(header);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
            }
            catch (JWTDecodeException exception){
                response.setHeader("Error", exception.getMessage());
            }
        }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (token != null) {
           Algorithm algorithm = HMAC512(SecurityConstants.SECRET.getBytes());
            DecodedJWT decodedJWT = JWT.require(algorithm).build()
                    .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""));
            String username = decodedJWT.getSubject();
            String [] roles = decodedJWT.getClaim(SecurityConstants.ROLES).asArray(String.class);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            stream(roles).forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role));
            });
            if (username != null) {
                return new UsernamePasswordAuthenticationToken(username, null, authorities);
            }
            return null;
        }
        return null;
    }
}

