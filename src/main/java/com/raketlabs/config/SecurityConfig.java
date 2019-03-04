package com.raketlabs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.raketlabs.repository.TokenRepository;
import com.raketlabs.service.LoginServiceImpl;



@Configuration
@EnableWebSecurity
@ComponentScan("com.raketlabs")
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

	@Autowired
	@Qualifier("persistentTokenRepository")
	private PersistentTokenRepository persistentTokenRepository;
	
	@Autowired
	LoginServiceImpl loginServiceImpl;
	
	@Autowired
	TokenRepository tokenRepository;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(loginServiceImpl);
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(loginServiceImpl);
		authenticationProvider.setPasswordEncoder(passwordEncoder());

		return authenticationProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		http.authorizeRequests().antMatchers("/css/**").permitAll();
		http.authorizeRequests().antMatchers("/webjars/**").permitAll();

		http
		.authorizeRequests()
		.antMatchers("/login", "/user/signup", "/user/register")
		.permitAll();
		
		http
		.authorizeRequests()
		.antMatchers("/", "/home")
		.access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')");
		
		http
		.authorizeRequests()
		.antMatchers("/user/list")
		.access("hasRole('ROLE_ADMIN')");

		http
		.authorizeRequests()
		.anyRequest()
		.authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.failureUrl("/login?error=true")
		.usernameParameter("username")
		.passwordParameter("password")
		.and()
		.logout()
		.logoutUrl("/j_spring_security_logout")
		.logoutSuccessUrl("/login")
		.and()
		.rememberMe()
		.tokenRepository(persistentTokenRepository)
		.tokenValiditySeconds(60*60)
		.and()
		.exceptionHandling()
		.accessDeniedPage("/accessDenied");
	}
}
