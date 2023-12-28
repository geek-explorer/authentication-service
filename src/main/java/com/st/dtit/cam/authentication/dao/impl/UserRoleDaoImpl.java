package com.st.dtit.cam.authentication.dao.impl;

import com.st.dtit.cam.authentication.dao.UserRoleDao;
import com.st.dtit.cam.authentication.enums.TableProperty;
import com.st.dtit.cam.authentication.model.UserRoles;
import org.hibernate.HibernateException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserRoleDaoImpl extends GenericDaoImpl<UserRoles, Long> implements UserRoleDao {

        @Override
        @Cacheable(value="user_roles", key = "#filterCriteria")
        public List<UserRoles> findUserRoles(Map<TableProperty, Object> filterCriteria) throws HibernateException {
               return findByFilterCriteria(filterCriteria);
        }
}
