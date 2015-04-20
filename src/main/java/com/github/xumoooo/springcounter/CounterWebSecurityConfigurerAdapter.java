package com.github.xumoooo.springcounter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;


@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class CounterWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private final static String COUNTER_ROLE = "COUNTER_ROLE";
    private final static String EMPTY_ROLE = "EMPTY_ROLE";


    @Autowired
    DigestAuthenticationEntryPoint digestAuthenticationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser("user").password("user").roles(COUNTER_ROLE)
                .and()
                .withUser("admin").password("admin").roles(COUNTER_ROLE)
                .and()
                .withUser("other").password("other").roles(EMPTY_ROLE);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests().antMatchers("/").hasRole(COUNTER_ROLE)
                .and()
                .exceptionHandling().accessDeniedPage("/403").authenticationEntryPoint(digestAuthenticationEntryPoint)
                .and()
                .addFilter(digestAuthenticationFilter(digestAuthenticationEntryPoint));
    }

    public DigestAuthenticationFilter digestAuthenticationFilter(DigestAuthenticationEntryPoint digestAuthenticationEntryPoint) throws Exception {
        DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
        digestAuthenticationFilter.setAuthenticationEntryPoint(digestAuthenticationEntryPoint);
        digestAuthenticationFilter.setUserDetailsService(userDetailsServiceBean());
        return digestAuthenticationFilter;
    }
}
