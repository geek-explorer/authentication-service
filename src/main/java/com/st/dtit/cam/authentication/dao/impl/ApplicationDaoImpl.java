package com.st.dtit.cam.authentication.dao.impl;

import com.st.dtit.cam.authentication.dao.ApplicationDao;
import com.st.dtit.cam.authentication.model.Application;
import org.hibernate.HibernateException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class ApplicationDaoImpl  extends GenericDaoImpl<Application, String> implements ApplicationDao {

        @Override
        @Cacheable(value="application", key = "#id", unless="#result == null")
        public Application findApplicationById(String id) throws HibernateException {
               return findById(id);
        }
}
