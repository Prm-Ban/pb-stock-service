package com.sunwell.stock.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
 
    private static final String RESOURCE_ID = "my_rest_api";
    
//    @Autowired
//    TokenStore tokenStore;
     
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.resourceId(RESOURCE_ID).stateless(false);
//    }
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
//        anonymous().disable()
//        .requestMatchers().antMatchers("/resources/**")
//        .and()
        .requestMatchers().antMatchers("/resources/**")
		.and()
        .authorizeRequests()
        	.antMatchers("/resources/**").authenticated()
//        .permitAll()
//        .access("hasRole('ADMIN')")
        .and()
        .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
    
//    @Override
//    public void configure(final ResourceServerSecurityConfigurer config) {
//        config.tokenServices(tokenServices());
//    }
//
//    @Bean
//    @Primary
//    public DefaultTokenServices tokenServices() {
//        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//        defaultTokenServices.setTokenStore(tokenStore);
//        return defaultTokenServices;
//    }

 
}