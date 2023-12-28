package com.st.dtit.cam.authentication.config;

import com.st.dtit.cam.authentication.utility.ldap.LdapAuthenticationProvider;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

       private Environment environment;

       public SecurityConfiguration(Environment environment){
             this.environment = environment;
       }

       @Override
       public void configure(AuthenticationManagerBuilder authenticationManager) throws Exception {
              authenticationManager.authenticationProvider(new LdapAuthenticationProvider(environment)).eraseCredentials(false);
       }

       @Bean
       static BeanFactoryPostProcessor removeErrorSecurityFilter() {
              return (beanFactory) ->
                      ((DefaultListableBeanFactory)beanFactory).removeBeanDefinition("errorPageSecurityInterceptor");
       }


       protected void configure(HttpSecurity httpSecurity) throws Exception{
              httpSecurity.cors()
                          .and()
                          .csrf()
                          .disable()
                          .authorizeRequests()
                          .antMatchers("/api/v1/cam/authentication/jwt-auth/refresh-token").permitAll()
                          .antMatchers("/api/v1/cam/authentication/jwt-auth/authenticate").permitAll()
                          .anyRequest()
                          .authenticated()
                          .and()
                          .httpBasic();
       }


       @Bean
       public AuthenticationManager authenticationManagerBean() throws Exception {
              return super.authenticationManagerBean();
       }

}
