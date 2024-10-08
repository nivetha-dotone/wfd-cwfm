package com.wfd.dot1.cwfm.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	 private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		  logger.info("This is a log message");
		  http
          .authorizeRequests(authorize -> authorize
              .antMatchers("/login").permitAll() // Allow access to login page without authentication
              .anyRequest().authenticated()
          )
          .formLogin(login -> login
              .loginPage("/login")
              .defaultSuccessUrl("/index")
              .permitAll()
          )
          .logout(logout -> logout
              .permitAll()
          );
	    logger.info("This is a log after message");
	}
	
	

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
            .withUser("user").password("{noop}password").roles("USER")
            .and()
            .withUser("admin").password("{noop}password").roles("ADMIN");
    }
}


