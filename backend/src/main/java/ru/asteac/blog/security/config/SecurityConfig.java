package ru.asteac.blog.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.asteac.blog.security.jwt.AuthTokenFilter;
import ru.asteac.blog.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        //securedEnabled = true,
        //jsr250Enabled = true,
        prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthTokenFilter authTokenFilter;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationEntryPoint unauthorizedHandler;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, AuthTokenFilter authTokenFilter, AuthenticationEntryPoint unauthorizedHandler) {
        this.userDetailsService = userDetailsService;
        this.authTokenFilter = authTokenFilter;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_MODERATOR > ROLE_USER");
        return roleHierarchy;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and();

        // Set session management to stateless
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        // Set permissions on endpoints
        http.authorizeRequests()
                .antMatchers("/api/v/0/auth/**").permitAll()
                .antMatchers("/api/v/0/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
}
