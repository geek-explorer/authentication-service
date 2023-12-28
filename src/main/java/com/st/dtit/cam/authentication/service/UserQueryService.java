package com.st.dtit.cam.authentication.service;

import com.st.dtit.cam.authentication.bean.response.Response;
import com.st.dtit.cam.authentication.enums.TableProperty;

import java.io.Serializable;
import java.util.Map;

public interface UserQueryService extends Serializable {

        Response getAllAuthenticationUsers(final String jwtToken) throws Exception;

        Response getAuthenticationUsersByFilterCriteria(final Map<TableProperty, Object> filterCriteria, final String jwtToken) throws Exception;

}
