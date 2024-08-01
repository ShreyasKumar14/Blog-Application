package com.shreyas.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shreyas.blog.payloads.JwtAuthRequest;
import com.shreyas.blog.payloads.JwtAuthResponse;
import com.shreyas.blog.security.JwtTokenHelper;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("login")
	public ResponseEntity<JwtAuthResponse>createToken(@RequestBody JwtAuthRequest request) throws Exception{
		JwtAuthResponse jwtResponse = new JwtAuthResponse();
		Boolean authenticate = false;
		
		this.authenticate(request.getUsername(),request.getPassword());
		
		UserDetails userDetails = this.userDetailService.loadUserByUsername(request.getUsername());
		
		String jwtToken = this.jwtTokenHelper.generateToken(userDetails);
		
		jwtResponse.setToken(jwtToken);
		
		return ResponseEntity.ok(jwtResponse);
	}

	private void authenticate(String username, String password) throws Exception {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);
			try {
				this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
			}catch(Exception e) {
				throw new Exception(e.getMessage());
			}
		
	}
}
