package com.st.dtit.cam.authentication.dao;

import com.st.dtit.cam.authentication.enums.TableProperty;
import com.st.dtit.cam.authentication.model.Role;
import com.st.dtit.cam.authentication.model.composite.AppRoleId;
import org.hibernate.HibernateException;

import java.util.List;
import java.util.Map;

public interface RoleDao extends GenericDao<Role, AppRoleId> {

        List<Role> findRole(final Map<TableProperty, Object> filterCriteria) throws HibernateException;

}
