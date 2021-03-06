package com.writings.security;

import com.writings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       // @formatter:off
        http
                .authorizeRequests()
                .antMatchers("/","/about","/register")
                .permitAll()
                .antMatchers(
                        "/js/*",
                        "/css/*",
                        "/img/*")
                .permitAll()
                .antMatchers("/addstatus",
                        "/editstatus",
                        "/deletestatus",
                        "/viewstatus")
                .hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll();
        // @formatter:on
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        // @formatter:off
        auth
                .inMemoryAuthentication()
                .withUser("prastik")
                .password("{noop}hello")
                .roles("USER");

        // @formatter:on

    }
    // Last step where the code feels - (la hai yo login gareko user chai fully authentic cha, so eslai login garna diye huncha!!)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}
