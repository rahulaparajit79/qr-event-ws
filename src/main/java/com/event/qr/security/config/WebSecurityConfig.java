package com.event.qr.security.config;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.event.qr.security.service.JwtUserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private AuthenticationEntryPoint authEntryPoint;
	@Autowired
	private JwtUserDetailsServiceImpl userDetailsService;

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder
		.userDetailsService(this.userDetailsService)
		.passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
		.csrf().disable()
		.authorizeRequests()
		//	.anyRequest().permitAll()
		.antMatchers("/client/*","/student/register","/user/bcrypt",
				"/course/listall","/student/emailTest","/downloads/**","/course/coursebycoursetype",
				"/course/coursetypes","/course/coursedetailbycoursetype","/forgotpassword/**").permitAll()
		//		.antMatchers("/client/client_Aproval","/client/client_registrationlist"
		//				,"/client/client_activeDeactive","/client/client_searchCompony").authenticated()
		.anyRequest().authenticated()
		.and().httpBasic().authenticationEntryPoint(authEntryPoint)
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}



	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(new ArrayList(Arrays.asList("*")));
		configuration.setAllowedMethods(new ArrayList(Arrays.asList("HEAD",
				"GET", "POST", "PUT", "DELETE", "PATCH")));
		// setAllowCredentials(true) is important, otherwise:
		// The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
		configuration.setAllowCredentials(true);
		// setAllowedHeaders is important! Without it, OPTIONS preflight request
		// will fail with 403 Invalid CORS request
		configuration.setAllowedHeaders(new ArrayList(Arrays.asList("Authorization", "Cache-Control", "Content-Type")));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	//		@Autowired
	//		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	//			auth.inMemoryAuthentication().withUser("sseidth").password("{noop}L1M1tEdTr@NsPort").roles("ADMIN");
	//		}


}
