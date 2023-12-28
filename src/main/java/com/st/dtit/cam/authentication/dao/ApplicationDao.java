package com.st.dtit.cam.authentication.dao;


import com.st.dtit.cam.authentication.model.Application;
import org.hibernate.HibernateException;

public interface ApplicationDao extends GenericDao<Application, String> {

       Application findApplicationById(String id) throws HibernateException;
}
