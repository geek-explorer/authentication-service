package com.st.dtit.cam.authentication.utility.builder;

import com.st.dtit.cam.authentication.bean.response.JwtRefreshResponse;

import java.io.Serializable;

public class JwtRefreshBuilder implements Serializable {

         private static final long serialVersionUID = 6810521319409732467L;

         private String username;

         private String jwToken;

         private String refreshToken;

         private String[] authorities;


         public JwtRefreshBuilder(){}

         public JwtRefreshBuilder withUserName(final String username) {
                this.username = username;
                return this;
         }

         public JwtRefreshBuilder withJwtToken(final String jwtToken) {
                this.jwToken = jwtToken;
                return this;
         }

         public JwtRefreshBuilder withRefreshToken(final String refreshToken){
                this.refreshToken = refreshToken;
                return this;
         }

         public JwtRefreshBuilder withAuthorities(final String[] authorities) {
                this.authorities = authorities;
                return this;
         }

         public JwtRefreshResponse build(){
                return new JwtRefreshResponse(this.username, this.jwToken, this.refreshToken, "Token", this.authorities);
         }
}
