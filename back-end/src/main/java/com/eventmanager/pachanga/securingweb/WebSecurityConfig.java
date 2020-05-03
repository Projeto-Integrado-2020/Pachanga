package com.eventmanager.pachanga.securingweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable();
			//.authorizeRequests()
				//.antMatchers("/", "/erro").permitAll()
				//.anyRequest().authenticated()
				//.and()
			//.formLogin()
				//.loginPage("/login")
				//.permitAll()
				//.and()
			//.logout()
				//.permitAll();
	}

	/*
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				//.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}
	
	*/
}