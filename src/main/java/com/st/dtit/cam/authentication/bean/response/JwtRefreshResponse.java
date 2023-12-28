package com.st.dtit.cam.authentication.bean.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
public class JwtRefreshResponse implements Serializable {

       private static final long serialVersionUID = 6771905678135345113L;

       @Getter
       private String username;

       @Getter
       private String jwToken;

       @Getter
       private String refreshToken;

       @Getter
       private String tokenType;

       @Getter
       private String[] authorities;

}
