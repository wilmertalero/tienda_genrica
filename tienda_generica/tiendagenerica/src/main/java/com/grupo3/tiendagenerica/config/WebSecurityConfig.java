package com.grupo3.tiendagenerica.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.grupo3.tiendagenerica.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	//Necesario para evitar que la seguridad se aplique a los resources
    //Como los css, imagenes y javascripts
    String[] resources = new String[]{
            "/include/**","/css/**","/icons/**","/img/**","/js/**","/layer/**"
    };
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
	        .antMatchers(resources).permitAll()  
	        .antMatchers("/","/index").permitAll()
	        .antMatchers("/Clientes*").access("hasRole('ROLE_ADMIN')")
	        .antMatchers("/Menu*").access("hasRole('ROLE_ADMIN')")
	        .antMatchers("/productos*").access("hasRole('ROLE_ADMIN')")
	        .antMatchers("/proveedores*").access("hasRole('ROLE_ADMIN')")
	        .antMatchers("/Reportes*").access("hasRole('ROLE_ADMIN')")
	        .antMatchers("/Usuarios*").access("hasRole('ROLE_ADMIN')")
	        .antMatchers("/userForm*").access("hasRole('ROLE_ADMIN')")
	        .antMatchers("/Ventas*").access("hasRole('ROLE_ADMIN')")
	        //.antMatchers("/user*").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/menu")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
            .logout()
                .permitAll()
                .logoutSuccessUrl("/login?logout");
    }
    BCryptPasswordEncoder bCryptPasswordEncoder;
    //Crea el encriptador de contraseñas	
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		//El numero 4 representa que tan fuerte quieres la encriptacion.
		//Se puede en un rango entre 4 y 31. 
		//Si no pones un numero el programa utilizara uno aleatoriamente cada vez
		//que inicies la aplicacion, por lo cual tus contraseñas encriptadas no funcionaran bien
        return bCryptPasswordEncoder;
    }
	
    @Autowired
    UserDetailsServiceImpl userDetailsService;
	
    //Registra el service para usuarios y el encriptador de contraseña
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
 
        // Setting Service to find User in the database.
        // And Setting PassswordEncoder
    	auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());     
    }

}
