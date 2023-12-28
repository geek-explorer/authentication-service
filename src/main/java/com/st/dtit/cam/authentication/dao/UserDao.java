package com.st.dtit.cam.authentication.dao;


import com.st.dtit.cam.authentication.enums.TableProperty;
import com.st.dtit.cam.authentication.model.ApplicationUser;
import org.hibernate.HibernateException;

import java.util.List;
import java.util.Map;

public interface UserDao extends GenericDao<ApplicationUser, String> {

         List<ApplicationUser> findUser(Map<TableProperty, Object> filterCriteria) throws HibernateException;
}
