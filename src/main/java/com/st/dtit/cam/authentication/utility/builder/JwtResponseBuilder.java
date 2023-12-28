package com.st.dtit.cam.authentication.utility.builder;

import com.st.dtit.cam.authentication.bean.response.JwtResponse;

import java.io.Serializable;
import java.util.List;

public class JwtResponseBuilder implements Serializable {

    private static final long serialVersionUID = -6347638082699432942L;

    private String username;
    private String lastName;
    private String firstName;
    private String applicationName;
    private String jwToken;
    private String refreshToken;
    private List<String> authorities;

    public JwtResponseBuilder(){}

    public JwtResponseBuilder withUserName(final String username){
         this.username = username;
         return this;
    }

    public JwtResponseBuilder withName(final String lastName, final String firstName){
         this.firstName = firstName;
         this.lastName = lastName;
         return this;
    }

    public JwtResponseBuilder withApplicationName(final String applicationName){
         this.applicationName = applicationName;
         return this;
    }

    public JwtResponseBuilder withJwToken(final String jwToken){
        this.jwToken = jwToken;
        return this;
    }

    public JwtResponseBuilder withRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public JwtResponseBuilder hasAuthorities(final List<String> authorities){
        this.authorities = authorities;
        return this;
    }

    public JwtResponse build(){
        return new JwtResponse(username, lastName, firstName,
                   applicationName, jwToken, refreshToken, authorities);
    }

}
