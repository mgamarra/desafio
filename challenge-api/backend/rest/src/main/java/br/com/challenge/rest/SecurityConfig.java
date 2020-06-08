package br.com.challenge.rest;

import br.com.challenge.rest.security.filter.CorsFilter;
import br.com.challenge.rest.security.filter.JwtFilter;
import br.com.challenge.rest.security.jwt.JwtAuthorizationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JwtFilter jwtAuthenticationTokenFilter() throws Exception {
        return new JwtFilter();
    }

    @Bean
    public CorsFilter securityRequestFilter() throws Exception {
        return new CorsFilter();
    }

    @Autowired
    private JwtAuthorizationEntryPoint jwtAuthorizationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthorizationEntryPoint).and()
                .addFilterBefore(securityRequestFilter(), ChannelProcessingFilter.class)
                .addFilterBefore(jwtAuthenticationTokenFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests().anyRequest().permitAll();

    }
}
