package com.st.dtit.cam.authentication.service;

import com.st.dtit.cam.authentication.bean.response.Response;
import com.st.dtit.cam.authentication.enums.TableProperty;

import java.io.Serializable;
import java.util.Map;

public interface RoleQueryService extends Serializable {

        Response getRolesByFilterCriteria(final Map<TableProperty, Object> filterCriteria, final String jwtToken) throws Exception;

}
