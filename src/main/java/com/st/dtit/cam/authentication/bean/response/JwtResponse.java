package com.st.dtit.cam.authentication.bean.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = 7588022735391999778L;

    @Getter
    private String username;

    @Getter
    private String lastName;

    @Getter
    private String firstName;

    @Getter
    private String applicationName;

    @Getter
    private String jwToken;

    @Getter
    private String refreshToken;

    @Getter
    private List<String> authorities;
}
