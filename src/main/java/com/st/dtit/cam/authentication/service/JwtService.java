package com.st.dtit.cam.authentication.service;

import com.st.dtit.cam.authentication.bean.response.Response;

public interface JwtService {

       Response generateJwToken(final String username, final String authCredencial, final String applicationCode) throws Exception;

       Response getRefreshToken(final String token) throws Exception;

}
