package com.st.dtit.cam.authentication.service;

import com.st.dtit.cam.authentication.bean.response.Response;

import java.io.Serializable;

public interface ApplicationUserService extends Serializable {

        Response getUserDetailsAndRoles(final String userName, final String applicationName) throws Exception;

}
