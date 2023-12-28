package com.st.dtit.cam.authentication.utility.ldap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class LdapAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(LdapAuthenticationProvider.class);

    private Environment environment;

    private LdapContextSource contextSource;

    private LdapTemplate ldapTemplate;


    public LdapAuthenticationProvider(Environment environment) {this.environment = environment;}

    private void initContext(){
            contextSource = new LdapContextSource();
            contextSource.setUrl("ldap://" + environment.getProperty("spring.ldap.embedded.ldif") + ":" +
                                             environment.getProperty("spring.ldap.embedded.port"));
            contextSource.setAnonymousReadOnly(true);
            contextSource.setUserDn(environment.getProperty("spring.ldap.embedded.base-dn"));
            contextSource.setPassword("userPassword");
            contextSource.afterPropertiesSet();

            ldapTemplate = new LdapTemplate(contextSource);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            initContext();
            Filter filter = new EqualsFilter("uid", authentication.getName());

            Boolean authenticate = ldapTemplate.authenticate(LdapUtils.emptyLdapName(), filter.encode(), authentication.getCredentials().toString());
            if (authenticate){
                  UserDetails userDetails = new User(authentication.getName(), authentication.getCredentials().toString(), new ArrayList<>());
                  Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials().toString(), new ArrayList<>());
                  return auth;
            }
            return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
