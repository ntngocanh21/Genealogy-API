package com.api.genealogy.config;

import com.api.genealogy.security.JwtAuthenticationEntryPoint;
import com.api.genealogy.security.JwtAuthenticationProvider;
import com.api.genealogy.security.JwtAuthenticationTokenFilter;
import com.api.genealogy.security.JwtSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtAuthenticationProvider authenticationProvider;
	
	@Autowired
    private JwtAuthenticationEntryPoint entryPoint;
	
	/**
	 * Similarity, we also need a object to initialize, create and destroy to manage ???.
	 * ??? => 
	 */
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(Collections.singletonList(authenticationProvider));
	}
	/**
	 * Understand, we create a Bean like objects. We will use it as "xuong song" in system.
	 * Like module in Android.
	 * This method is for providing object "JwtAuthenticationTokenFilter"
	 * @return
	 * 
	 * Purpose: Handler all request to API need to access token or announce error.
	 */
	@Bean
	public JwtAuthenticationTokenFilter authenticationTokenFilter() {
		JwtAuthenticationTokenFilter filter = new JwtAuthenticationTokenFilter();
		/**
		 * It means if - else
		 * if (has token) call parent to execute
		 * else announce error from parent.
		 * Another else call interface AuthenticationSuccessHandler 
		 */
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new JwtSuccessHandler());
        return filter;
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .anyRequest().authenticated() // Các request còn lại đều cần được authenticated
                .and()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
    }
}
