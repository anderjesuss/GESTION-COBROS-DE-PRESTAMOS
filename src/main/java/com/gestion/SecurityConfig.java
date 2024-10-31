package com.gestion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.gestion.security.Security;

//archivo de configuración
@Configuration
//habilitar seguridad
@EnableWebSecurity
//habilitar método para validar clave
@EnableMethodSecurity
public class SecurityConfig{
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		/*http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/", "/home").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
				.loginPage("/validar/usuario")
				.permitAll()
			)
			.logout((logout) -> logout.permitAll());*/
		http.csrf().disable().authorizeHttpRequests().
		requestMatchers("/validar/**","/resources/**","/error/**").permitAll().
		and().authorizeHttpRequests().
		requestMatchers("/prestamista/**").authenticated().
		requestMatchers("/usuario/**").authenticated().
		requestMatchers("/solicitud/**").authenticated().
		requestMatchers("/cuenta/**").authenticated().
		requestMatchers("/prestamo/**").authenticated().
		requestMatchers("/pago/**").authenticated().
		requestMatchers("/estado/**").authenticated().
		requestMatchers("/rendimiento/**").authenticated().
		requestMatchers("/reporte/**").authenticated().
		and().formLogin().loginPage("/validar/usuario").
		defaultSuccessUrl("/validar/dashboard");

		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new Security();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
		dao.setUserDetailsService(userDetailsService());
		dao.setPasswordEncoder(password());
		return dao;
	}
	
	@Bean
	public BCryptPasswordEncoder password(){
		return new BCryptPasswordEncoder();
	}
}