package com.st.dtit.cam.authentication.dao.impl;

import com.st.dtit.cam.authentication.dao.RoleDao;
import com.st.dtit.cam.authentication.enums.TableProperty;
import com.st.dtit.cam.authentication.model.Role;
import com.st.dtit.cam.authentication.model.composite.AppRoleId;
import org.hibernate.HibernateException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class RoleDaoImpl extends GenericDaoImpl<Role, AppRoleId> implements RoleDao {

        @Override
        @Cacheable(value = "role", key = "#filterCriteria")
        public List<Role> findRole(Map<TableProperty, Object> filterCriteria) throws HibernateException {
               return findByFilterCriteria(filterCriteria);
        }

}
