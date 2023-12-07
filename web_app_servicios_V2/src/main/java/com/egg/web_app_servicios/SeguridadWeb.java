/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.web_app_servicios;

import com.egg.web_app_servicios.repositorios.UsuarioRepositorio;

import com.egg.web_app_servicios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;



import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SeguridadWeb{
    
     @Autowired
    public UsuarioService usuarioService;  
     
      @Autowired
    public UsuarioRepositorio usuarioRepositorio;  
     
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(usuarioService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeHttpRequests((requests) -> requests
                                .requestMatchers ("/admin/*").hasRole("ADMIN")
				.requestMatchers("/css/*", "/js/*","/img/*","/**").permitAll()
				.anyRequest().authenticated()
                                                                     
			)
			.formLogin((form) -> form
				.loginPage("/login")
                                .loginProcessingUrl("/logincheck")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/inicio")
				.permitAll()
			)
			.logout((logout) -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/")
                                .permitAll());                   
                        
		return http.build() ;
        }    
              
                

        
        
}