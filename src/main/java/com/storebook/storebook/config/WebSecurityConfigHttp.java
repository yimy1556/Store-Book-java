package com.storebook.storebook.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class WebSecurityConfigHttp extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/authors", "/api/authors/**/books").hasRole("SUPERADMIN")
                .antMatchers(HttpMethod.POST, "/api/stores", "/api/stores/**/books/**").hasAnyAuthority("SUPERADMIN", "ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/stores/**/books/**").hasAnyAuthority("SUPERADMIN", "ADMIN")
                .antMatchers("/api/customers/**/books", "/api/customers/**").hasAnyAuthority("CUSTOMER")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        httpSecurity.formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/api/login");

        httpSecurity.logout().logoutUrl("/api/logout");

        httpSecurity.csrf().disable();

    }
}
