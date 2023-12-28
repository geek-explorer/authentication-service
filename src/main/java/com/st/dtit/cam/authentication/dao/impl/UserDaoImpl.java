package com.st.dtit.cam.authentication.dao.impl;

import com.st.dtit.cam.authentication.dao.UserDao;
import com.st.dtit.cam.authentication.enums.TableProperty;
import com.st.dtit.cam.authentication.model.ApplicationUser;
import org.hibernate.HibernateException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl extends GenericDaoImpl<ApplicationUser, String> implements UserDao {

        @Override
        @Cacheable(value="user", key = "#filterCriteria")
        public List<ApplicationUser> findUser(Map<TableProperty, Object> filterCriteria) throws HibernateException {
               return findByFilterCriteria(filterCriteria);
        }
}
