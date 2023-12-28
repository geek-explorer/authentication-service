package com.st.dtit.cam.authentication.dao;

import com.st.dtit.cam.authentication.enums.TableProperty;
import org.hibernate.HibernateException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GenericDao<T, PK extends Serializable> {

       T insert(T entity) throws HibernateException;

       List<T> batchInsert(List<T> entityList) throws HibernateException;

       T update(T entity) throws HibernateException;

       void delete(PK id) throws HibernateException;

       void delete(T entity) throws HibernateException;

       void batchDelete(List<T> entityList) throws HibernateException;

       T findById(PK id) throws HibernateException;

       List<T>  findAll() throws HibernateException;

       List<Map<String, Object>>   findByFilterCriteria(Map<TableProperty, Object> filterCriteria, List<TableProperty> properties) throws HibernateException;

       List<Map<String, Object>>   findByFilterOrCriteria(Map<TableProperty, Object> filterCriteria, List<TableProperty> properties) throws HibernateException;

       List<T>  findByFilterCriteria(Map<TableProperty, Object> filterCriteria) throws HibernateException;

       List<T>  findByFilterOrCriteria(Map<TableProperty, Object> filterCriteria) throws HibernateException;

       T makePersistent(T entity) throws HibernateException;

       void evict(T entity) throws HibernateException;
}
