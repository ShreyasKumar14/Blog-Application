package com.shreyas.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{     //This class acts as a middleware for all the APIs.

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestToken = request.getHeader("Authorization");
		String userName=null;
		String token=null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer")) {
			token =  requestToken.substring(7);
			try {
				userName = this.jwtTokenHelper.getUsernameFromToken(token);
			}catch(IllegalArgumentException e) {
				System.out.println("Unable to get jwt token");
			}catch(ExpiredJwtException e) {
				System.out.println("Jwt token expired");
			}catch(MalformedJwtException e) {
				System.out.println("Invalid jwt token!");
			}
		}else {
			System.out.println("No token provided or JWT token is not a 'bearer' token");
		}
		if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
			if(this.jwtTokenHelper.validateToken(token, userDetails)) {   // Here we validate the token by comparing the username inside the token with the username in userDetails class(captured by spring security).
				
				//UsernamePasswordAuthenticationToken is a class in the Spring Security framework that represents an authentication token. It is used to encapsulate user authentication information, such as a username
				//and password, and can also hold additional details like granted authorities (roles and permissions).
				//In the context of JWT authentication, UsernamePasswordAuthenticationToken is used to set the authentication in the SecurityContext after validating the JWT token.
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken); //The token is stored in the SecurityContext to represent the authenticated user.
			}else {
				System.out.println("Invalid jwt token");
			}
		}else {
			System.out.println("Username is null or security context is not empty");
		}
		filterChain.doFilter(request, response);
	}

}
