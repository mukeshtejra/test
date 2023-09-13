package com.springboot.security.jwt.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springboot.security.jwt.security.JwtAuthenticationEntryPoint;
import com.springboot.security.jwt.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {
		
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf(csrf ->csrf.disable())
		    .cors(cors ->cors.disable())
		    .authorizeHttpRequests(auth -> 
		                        auth.requestMatchers("/home/**")
		                        .authenticated()
		                        .requestMatchers("/auth/login")
		                        .permitAll()
		                        .anyRequest()
		                        .authenticated()
		                        )
		    .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
		    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		
		;
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
		
	}
}
