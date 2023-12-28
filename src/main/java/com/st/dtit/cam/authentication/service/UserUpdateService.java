package com.st.dtit.cam.authentication.service;

import com.st.dtit.cam.authentication.bean.param.ApplicationUserParameter;
import com.st.dtit.cam.authentication.bean.response.Response;

import java.io.Serializable;

public interface UserUpdateService extends Serializable {

         Response updateApplicationUser(final ApplicationUserParameter inputParameter, final String jwtToken) throws Exception;

}
