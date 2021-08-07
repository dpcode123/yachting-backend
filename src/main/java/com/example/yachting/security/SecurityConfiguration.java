package com.example.yachting.security;

import com.example.yachting.security.jwt.JwtAuthenticationEntryPoint;
import com.example.yachting.security.jwt.JwtFilter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtFilter jwtFilter;

    public SecurityConfiguration(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtFilter jwtFilter){
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/**/public/**").permitAll()
                .antMatchers("/api/**/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/h2-console/");
    }


}
