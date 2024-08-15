package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity /*<-- now in step 2 uncomment this annotation*/
public class SpringSecurityConfig
{
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception
	{
//		now step 3 uncomment the below line and comment the line with request matchers. now we are good to go
		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
//		http.authorizeHttpRequests((requests) -> requests.requestMatchers("/public/**").permitAll().requestMatchers("/user/**").hasRole("USER").requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated());
		http.httpBasic(Customizer.withDefaults());
		http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService()
	{
		UserDetails user = User.withUsername("user").password("{noop}user").roles("USER").build();
		UserDetails admin = User.withUsername("admin").password("{noop}admin").roles("ADMIN").build();
		
		return new InMemoryUserDetailsManager(user,admin);
	}
}
