package com.glca.emp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.glca.emp.service.UserDetailsServiceImpl;

@SuppressWarnings("deprecation")
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
		daoAuthProvider.setUserDetailsService(userDetailsService());
		daoAuthProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeRequests().antMatchers("/emp/add-user", "/emp/add-role").permitAll()
				.antMatchers(HttpMethod.POST, "/emp/add-employee").hasAuthority("ADMIN")
				.antMatchers(HttpMethod.PUT, "/emp/update-employee").hasAuthority("ADMIN")
				.antMatchers(HttpMethod.GET, "/emp/get-employees").hasAnyAuthority("USER", "ADMIN")
				.antMatchers(HttpMethod.GET, "/emp/get-employee-byid/**").hasAnyAuthority("ADMIN", "USER")
				.antMatchers(HttpMethod.DELETE, "/emp/delete-employee/**").hasAuthority("ADMIN")
				.antMatchers(HttpMethod.GET, "/EmployeeApp/emp/search-employee/**").hasAnyAuthority("ADMIN", "USER")
				.antMatchers(HttpMethod.GET, "/EmployeeApp/emp/sort-asc").hasAnyAuthority("ADMIN", "USER")
				.antMatchers(HttpMethod.GET, "/EmployeeApp/emp/sort-dsc").hasAnyAuthority("ADMIN", "USER").anyRequest()
				.authenticated().and().httpBasic().and().cors().and().csrf().disable();
		// do authority for search
	}

}
