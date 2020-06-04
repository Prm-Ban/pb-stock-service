package com.sunwell.stock.config;

import java.security.MessageDigest;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
//@EnableWebMvcSecurity
//@Order(1)
public class IgnoringSecurityConfiguration extends WebSecurityConfigurerAdapter
{
		
    @Override
    public void configure(WebSecurity security)
    {
        security.ignoring().antMatchers("/**");
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception
    {
        security
        		.requestMatchers().antMatchers("/*")
        		.and()
                .authorizeRequests()
//                	.antMatchers("/oauth/token").denyAll()
                    .antMatchers("/", "/test", "/application.properties.disabled").permitAll()
//                    .antMatchers("/secure/**").hasAuthority("USER")
//                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    .anyRequest().authenticated()
//                .and().formLogin()
//                    .loginPage("/log")
//                    .defaultSuccessUrl("/welcome/")
//                    .failureUrl("/login?error")
//                    .usernameParameter("username")
//                    .passwordParameter("password")
//                    .permitAll()
                .and().logout()
                    .logoutUrl("/logout").logoutSuccessUrl("/login?loggedOut")
                    .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                    .permitAll()
                .and().sessionManagement()
                    .sessionFixation().changeSessionId()
                    .maximumSessions(1).maxSessionsPreventsLogin(true)
                .and().and().csrf().disable();
        		
    }
}
