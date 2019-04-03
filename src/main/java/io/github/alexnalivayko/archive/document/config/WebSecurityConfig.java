package io.github.alexnalivayko.archive.document.config;

import io.github.alexnalivayko.archive.document.service.security.AuthenticationSuccessHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.requestMatchers()
				.denyAll()
				.antMatchers("/console")
				.access("hasRole('ADMIN')")
				.antMatchers("/static/**")
				.permitAll()
				.anyRequest()
				.access("hasRole('USER')")
				.and()
				.formLogin()
				.successHandler(authenticationSuccessHandler())
				.loginPage("/dashboard/login")
				.permitAll()
				.and()
				.csrf()
				.disable()
				.logout()
				.and()
				.headers()
				.frameOptions()
				.sameOrigin();
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new AuthenticationSuccessHandlerImpl();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User
				.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build());

		manager.createUser(User
				.withDefaultPasswordEncoder()
				.username("admin")
				.password("password")
				.roles("ADMIN")
				.build());

		return manager;
	}
}