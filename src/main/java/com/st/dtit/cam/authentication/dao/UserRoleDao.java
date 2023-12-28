package com.st.dtit.cam.authentication.dao;


import com.st.dtit.cam.authentication.enums.TableProperty;
import com.st.dtit.cam.authentication.model.UserRoles;
import org.hibernate.HibernateException;

import java.util.List;
import java.util.Map;

public interface UserRoleDao extends GenericDao<UserRoles, Long> {

       List<UserRoles> findUserRoles(Map<TableProperty, Object> filterCriteria) throws HibernateException;
}
