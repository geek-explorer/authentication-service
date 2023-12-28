package com.st.dtit.cam.authentication.dao;

import com.st.dtit.cam.authentication.enums.TableProperty;
import com.st.dtit.cam.authentication.model.AuthToken;
import com.st.dtit.cam.authentication.model.composite.AuthTokenId;
import org.hibernate.HibernateException;

import java.util.Map;
import java.util.Optional;

public interface AuthenticationTokenDao extends GenericDao<AuthToken, AuthTokenId>{

        Optional<AuthToken> findAuthenticationTokenById(final AuthTokenId authTokenId) throws HibernateException;

        Optional<AuthToken> findAuthenticationTokenByTokenId(final Map<TableProperty, Object> filterCriteria) throws HibernateException;

        void updateAuthenticationToken(final AuthToken authToken) throws HibernateException;

}
