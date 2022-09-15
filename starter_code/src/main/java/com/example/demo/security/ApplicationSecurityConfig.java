package com.example.demo.security;

import com.example.demo.security.jwt.CustomAuthorizationFilter;
import com.example.demo.security.jwt.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    //there are many diff ways how to tell spring how to look for the users;
    //one of them is user details service provided by Spring Security:
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager());
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");

                http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers(POST,"/api/user/**").permitAll();
        http.authorizeRequests().antMatchers(POST,"api/login/**").permitAll();

        http.authorizeRequests().antMatchers("/api/cart/**").hasAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers("/api/cart/**").hasAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers("/api/item/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/api/order/**").hasAuthority("ROLE_ADMIN");
       // http.authorizeRequests().antMatchers(POST,"/api/order/submit").hasAuthority("ROLE_USER");

        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(jwtAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated();
        http.headers().frameOptions().disable();
    }
}