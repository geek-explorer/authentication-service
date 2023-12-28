package com.st.dtit.cam.authentication.utility.parameter;

import com.st.dtit.cam.authentication.enums.QueryOperator;
import org.hibernate.HibernateException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;

public interface QueryRule<T> extends Serializable {

       Predicate apply(Root<T> tableDefRoot, CriteriaBuilder builder, QueryOperator queryOperator) throws HibernateException;
}
