package com.st.dtit.cam.authentication.bean.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthenticationParameter implements Serializable {

        private String applicationCode;

        private String userName;
}
