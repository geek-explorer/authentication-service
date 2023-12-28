package com.st.dtit.cam.authentication.dao.impl;

import com.st.dtit.cam.authentication.dao.GenericDao;
import com.st.dtit.cam.authentication.enums.DataSourceEnum;
import com.st.dtit.cam.authentication.enums.QueryOperator;
import com.st.dtit.cam.authentication.enums.TableProperty;
import com.st.dtit.cam.authentication.utility.HibernateUtil;
import com.st.dtit.cam.authentication.utility.parameter.KeyValueRule;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.st.dtit.cam.authentication.enums.DataSourceEnum.DB_SOURCE_AUTH;

public class GenericDaoImpl<T, ID extends Serializable> implements GenericDao<T, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericDaoImpl.class);

    private Class<T> persistentClass;

    public GenericDaoImpl(){
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T insert(T entity) throws HibernateException {
            Session session = null;
            try {
                session = getDatabaseSession(DB_SOURCE_AUTH);
                if (null != session && session.isConnected())  {
                    session.save(entity);
                    session.flush();
                    session.getTransaction().commit();
                }   else    {
                    LOGGER.info("Session is close. Error on Entity {} creation.", entity.toString());
                    return null;
                }
            }   catch (Exception ex)    {
                LOGGER.error("Error on Entity {} creation.", entity.toString(), ex);
                clearSession(session);
                throw new HibernateException(ex);
            }   finally {
                closeSession(session);
            }
            return entity;
    }

    @Override
    public List<T> batchInsert(List<T> entityList) throws HibernateException {
            final Session session = getDatabaseSession(DB_SOURCE_AUTH);
            try {
                if (null != session && session.isConnected()) {
                    entityList.stream().forEach(entity -> session.save(entity));
                    session.flush();
                    session.getTransaction().commit();
                }   else {
                    LOGGER.info("Session is close. Error on batch Entity creation.");
                    return null;
                }
                closeSession(session);
            } catch (Exception ex) {
                LOGGER.info("Error on batch Entity creation.", entityList.size());
                clearSession(session);
                throw new HibernateException(ex);
            }
            return entityList;
    }

    @Override
    public T update(T entity) throws HibernateException {
            Session session = null;
            try {
                session = getDatabaseSession(DB_SOURCE_AUTH);
                if (null != session && session.isConnected()) {
                    session.update(entity);
                    session.flush();
                    session.getTransaction().commit();
                } else {
                    LOGGER.info("Session is close. Error on Entity {} update.", entity.toString());
                    return null;
                }
            } catch (Exception ex) {
                LOGGER.error("Error on Entity {} update", entity.toString(), ex);
                clearSession(session);
                throw new HibernateException(ex);
            } finally {
                closeSession(session);
            }
            return entity;
    }

    @Override
    public void delete(ID id) throws HibernateException {
            Session session = null;
            try {
                session = getDatabaseSession(DB_SOURCE_AUTH);
                if (null != session && session.isConnected()) {
                    session.delete(id);
                    session.flush();
                    session.getTransaction().commit();
                } else {
                    LOGGER.info("Session is close. Error on Entity Id {} delete.", id.toString());
                }
            } catch (Exception ex) {
                LOGGER.error("Error on Entity Id {} delete", id.toString(), ex);
                throw new HibernateException(ex);
            } finally {
                closeSession(session);
            }
    }

    @Override
    public void delete(T entity) throws HibernateException {
            Session session = null;
            try {
                session = getDatabaseSession(DB_SOURCE_AUTH);
                if (null != session && session.isConnected()) {
                    session.delete(entity);
                    session.flush();
                    session.getTransaction().commit();
                } else {
                    LOGGER.info("Session is close. Error on Entity {} delete.", entity.toString());
                }
            } catch (Exception ex) {
                LOGGER.error("Error on Entity {} delete", entity.toString(), ex);
                throw new HibernateException(ex);
            } finally {
                closeSession(session);
            }
    }

    @Override
    public void batchDelete(List<T> entityList) throws HibernateException {
            try {
                final Session session = getDatabaseSession(DB_SOURCE_AUTH);
                if (null != session && session.isConnected()) {
                    entityList.stream().forEach(entity -> session.delete(entity));
                    session.flush();
                    session.getTransaction().commit();
                } else {
                    LOGGER.info("Session is close. Error on batch Entity {} delete.", entityList.toString());
                }
                closeSession(session);
            } catch (Exception ex) {
                LOGGER.error("Error on batch Entity {} delete", entityList.toString(), ex);
                throw new HibernateException(ex);
            }
    }

    @Override
    public T findById(ID id) throws HibernateException {
            T entity = null;
            Session session = null;
            try {
                session = getDatabaseSession(DB_SOURCE_AUTH);
                entity = session.get(this.persistentClass, id);
            } catch (Exception ex) {
                throw new HibernateException(ex);
            } finally {
                closeSession(session);
            }
            return entity;
    }

    @Override
    public List<T> findAll() throws HibernateException {
            Session session = null;
            try {
                session = getDatabaseSession(DB_SOURCE_AUTH);
                CriteriaBuilder builder = session.getCriteriaBuilder();
                CriteriaQuery<T> criteriaQuery = builder.createQuery(this.persistentClass);
                Root<T> tableDefRoot   = criteriaQuery.from(this.persistentClass);

                criteriaQuery.select(tableDefRoot).distinct(true);

                Query<T> query = session.createQuery(criteriaQuery).setMaxResults(5000);
                return query.getResultList();
            } catch (Exception ex) {
                throw new HibernateException(ex);
            } finally {
                closeSession(session);
            }
    }

    @Override
    public List<Map<String, Object>>  findByFilterCriteria(Map<TableProperty, Object> filterCriteria, List<TableProperty> properties) throws HibernateException {
            if (properties.size() > 1) {
                return findByFilterCriteriaWithMultiProperty(filterCriteria, properties, QueryOperator.AND);
            } else {
                return findByFilterCriteriaWithSingleProperty(filterCriteria, properties.get(0), QueryOperator.AND);
            }
    }

    @Override
    public List<Map<String, Object>>   findByFilterOrCriteria(Map<TableProperty, Object> filterCriteria, List<TableProperty> properties) throws HibernateException {
            if (properties.size() > 1) {
                return findByFilterCriteriaWithMultiProperty(filterCriteria, properties, QueryOperator.OR);
            } else {
                return findByFilterCriteriaWithSingleProperty(filterCriteria, properties.get(0), QueryOperator.OR);
            }
    }

    @Override
    public List<T>  findByFilterOrCriteria(Map<TableProperty, Object> filterCriteria) throws HibernateException {
            return  findByFilterCriteriaWithoutPropertyParam(filterCriteria, QueryOperator.OR);
    }

    @Override
    public List<T>  findByFilterCriteria(Map<TableProperty, Object> filterCriteria) throws HibernateException {
            return  findByFilterCriteriaWithoutPropertyParam(filterCriteria, QueryOperator.AND);
    }


    @Override
    public T makePersistent(T entity) throws HibernateException {
            Session session = null;
            try {
                session = getDatabaseSession(DB_SOURCE_AUTH);
                if (null != session && session.isConnected()) {
                    session.saveOrUpdate(entity);
                    session.flush();
                    session.getTransaction().commit();
                } else {
                    LOGGER.info("Session is close. Error on Entity {} make persistent.", entity.toString());
                    return null;
                }
            } catch (Exception ex) {
                LOGGER.error("Error on Entity {} make persistent.", entity.toString(), ex);
                throw new HibernateException(ex);
            } finally {
                closeSession(session);
            }
            return entity;
    }

    @Override
    public void evict(T entity) throws HibernateException {
            Session session = null;
            try {
                session = getDatabaseSession(DB_SOURCE_AUTH);
                if (null != session && session.isConnected()) {
                    session.evict(entity);
                } else {
                    LOGGER.info("Session is close. Error on Entity {} evict.", entity.toString());
                }
            } catch (Exception ex){
                LOGGER.error("Error on Entity {} evict.", entity.toString(), ex);
                throw new HibernateException(ex);
            } finally {
                closeSession(session);
            }
    }

    protected  Session getDatabaseSession(final DataSourceEnum dataSource) {
            LOGGER.info("Opening Database session for datasource {}", dataSource.getDataSource());
            Session session = null;
            switch (dataSource) {
                case DB_SOURCE_AUTH:
                    try {
                        session = HibernateUtil.getSessionFactoryAuth().openSession();
                    } catch (Exception ex) {
                        LOGGER.error("Error on initializing database session.", ex);
                    }
                    break;
                default:
                    break;
            }
            if (session != null && session.isOpen() && !session.getTransaction().isActive()){
                 session.beginTransaction();
            }
            return session;
    }

    private List<Map<String, Object>>  findByFilterCriteriaWithSingleProperty(Map<TableProperty, Object> filterCriteria, TableProperty property,
                                                                              QueryOperator queryOperator) throws HibernateException {
            Session session = null;
            try{
                session = getDatabaseSession(DB_SOURCE_AUTH);
                CriteriaBuilder builder = session.getCriteriaBuilder();

                CriteriaQuery<Object> criteriaQuery = builder.createQuery(Object.class);
                Root<T> tableDefRoot   = criteriaQuery.from(this.persistentClass);

                final KeyValueRule keyValueRule = KeyValueRule.has(filterCriteria);
                Predicate queryPredicate = keyValueRule.apply(tableDefRoot, builder, queryOperator);

                criteriaQuery.select(getSelection(tableDefRoot, property))
                        .where(queryPredicate).distinct(true);

                Query<Object> query = session.createQuery(criteriaQuery);
                List<Object> resultList = query.getResultList();
                final List<Map<String, Object>>  resultMap = resultList
                        .stream()
                        .map(row -> {
                            Map<String, Object> propertyMap = new HashMap<>();
                            propertyMap.put(property.getKey(), row);
                            return propertyMap;
                        })
                        .collect(Collectors.toList());
                return resultMap;
            } catch (Exception ex) {
                throw new HibernateException(ex);
            } finally {
                closeSession(session);
            }
    }

    private List<Map<String, Object>>  findByFilterCriteriaWithMultiProperty(Map<TableProperty, Object> filterCriteria,
                                                                             List<TableProperty> properties, QueryOperator queryOperator) throws HibernateException {
            Session session = null;
            try{
                session = getDatabaseSession(DB_SOURCE_AUTH);
                CriteriaBuilder builder = session.getCriteriaBuilder();

                CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
                Root<T> tableDefRoot   = criteriaQuery.from(this.persistentClass);

                final KeyValueRule keyValueRule = KeyValueRule.has(filterCriteria);
                Predicate queryPredicate = keyValueRule.apply(tableDefRoot, builder, queryOperator);

                List<Selection<?>> queryResponse = properties.stream()
                        .map(props -> getSelection(tableDefRoot, props))
                        .collect(Collectors.toList());

                criteriaQuery.multiselect(queryResponse)
                        .where(queryPredicate).distinct(true);

                Query<Object[]> query = session.createQuery(criteriaQuery);

                List<Object[]> resultList = query.getResultList();

                final List<Map<String, Object>>  resultMap = resultList
                        .stream()
                        .map(row -> {
                            Map<String, String> propertyMap = new HashMap<>();
                            return properties.stream()
                                    .collect(HashMap<String, Object>::new,
                                            (map, streamValue) -> map.put(streamValue.getKey(), row[map.size()]), (map, map2) ->{});

                        })
                        .collect(Collectors.toList());
                return resultMap;
            } catch (Exception ex){
                throw new HibernateException(ex);
            }finally {
                closeSession(session);
            }
    }

    private List<T>  findByFilterCriteriaWithoutPropertyParam(Map<TableProperty, Object> filterCriteria, QueryOperator queryOperator) throws HibernateException {
            Session session = null;
            try {
                session = getDatabaseSession(DB_SOURCE_AUTH);
                CriteriaBuilder builder = session.getCriteriaBuilder();
                CriteriaQuery<T> criteriaQuery = builder.createQuery(this.persistentClass);
                Root<T> tableDefRoot   = criteriaQuery.from(this.persistentClass);

                final KeyValueRule keyValueRule = KeyValueRule.has(filterCriteria);
                Predicate queryPredicate = keyValueRule.apply(tableDefRoot, builder,queryOperator);

                criteriaQuery.select(tableDefRoot)
                        .where(queryPredicate).distinct(true);

                Query<T> query = session.createQuery(criteriaQuery).setMaxResults(1000);
                return query.getResultList();
            } catch (Exception ex) {
                throw new HibernateException(ex);
            } finally {
                closeSession(session);
            }
    }


    private Selection<?> getSelection(final Root<T> tableDefRoot, TableProperty property){
            final String[] tableProperties =  property.getField().split("\\.");
            if (tableProperties.length > 1) {
                return tableDefRoot.get(tableProperties[0]).get(tableProperties[1]);
            } else {
                return tableDefRoot.get(tableProperties[0]);
            }
    }

    protected void closeSession(Session session){
            if (null != session && session.isOpen()) {
                session.close();
            }
    }

    protected void clearSession(Session session){
            if (null != session && session.isOpen()) {
                session.clear();
            }
    }
}
