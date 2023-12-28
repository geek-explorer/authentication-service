package com.st.dtit.cam.authentication.bean;

import org.springframework.security.core.GrantedAuthority;

public class AuthorityBean implements GrantedAuthority {

    private String authority;

    public AuthorityBean(String authority){
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
