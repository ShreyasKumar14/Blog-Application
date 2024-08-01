package com.shreyas.blog.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.shreyas.blog.repositories.UserRepo;
import com.shreyas.blog.security.CustomUserDetailService;
import com.shreyas.blog.security.JwtAuthenticationEntryPoint;
import com.shreyas.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebMvc
@EnableWebSecurity
public class SecurityConfig{
 
	public static final	String[] PUBLIC_URLS = {"/api/v1/auth/login","/v3/api-docs/**","/v2/api-docs/**","/swagger-ui/**","/swagger-resources/**", "/webjars/**"};
	@Autowired 
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;
	
	@Autowired 
	private JwtAuthenticationEntryPoint jwtAuthEntryPoint;
	
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 System.out.println("Filter");

         http.csrf(AbstractHttpConfigurer::disable)
                 .authorizeHttpRequests(request -> request.requestMatchers(PUBLIC_URLS).permitAll().anyRequest().authenticated())
                
                 .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  //Here we specify that no user credential will be stored on the server i.e it will be Stateless, jwt token will be used for session management that stores the user credentials.
                 .authenticationProvider(daoAuthenticationProvider())
                 .exceptionHandling(handling -> handling.authenticationEntryPoint(this.jwtAuthEntryPoint)
                 .and()
                 .addFilterBefore(this.jwtAuthFilter, UsernamePasswordAuthenticationFilter.class));

	        return http.build();
	    }
	 
	 @Bean
	 public PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	 }
	 
	 @Bean
	 public DaoAuthenticationProvider daoAuthenticationProvider() {         //This DaoAuthenticationProvider will fetch userDetails and then encode the password. The encoded password is authenticated from the database in securityFilterChain().
		 System.out.println("dao");
		 DaoAuthenticationProvider provider = new  DaoAuthenticationProvider();
		 provider.setUserDetailsService(this.customUserDetailService);
		 provider.setPasswordEncoder(passwordEncoder());
		 return provider;
	 }
	 
	 @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
	            throws Exception {
	        return config.getAuthenticationManager();
	    }
}
