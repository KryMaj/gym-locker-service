package com.gym.gym.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {




    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeRequests()
                .antMatchers( "/client/**").hasRole("ADMIN")
                .antMatchers( "/locker/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/cl/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/cl/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}admin").roles("ADMIN");
    }


}
