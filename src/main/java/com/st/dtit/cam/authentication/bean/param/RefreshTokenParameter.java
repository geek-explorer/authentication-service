package com.st.dtit.cam.authentication.bean.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class RefreshTokenParameter implements Serializable {

        private static final long serialVersionUID = 2590932059043519483L;

        private String refreshToken;
}
