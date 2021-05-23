package br.com.alura.mvc.mudi.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.csrf().disable()
		.antMatcher("/**")
        .authorizeRequests()
        .antMatchers("/", "/login**","/callback/", "/webjars/**", "/css/**", "/error**", "/home**")
        .permitAll()
        .anyRequest()
        .fullyAuthenticated()
        .and()
        .formLogin().loginPage("/login")
        .defaultSuccessUrl("/usuario/pedido")
        .usernameParameter("username")
        .passwordParameter("password")
        .and()
        .logout().logoutUrl("/logout")
        .logoutSuccessUrl("/home");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
}