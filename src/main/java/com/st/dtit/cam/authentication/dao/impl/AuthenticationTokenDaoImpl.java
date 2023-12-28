package com.st.dtit.cam.authentication.dao.impl;

import com.st.dtit.cam.authentication.dao.AuthenticationTokenDao;
import com.st.dtit.cam.authentication.enums.TableProperty;
import com.st.dtit.cam.authentication.model.AuthToken;
import com.st.dtit.cam.authentication.model.composite.AuthTokenId;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;


import java.util.Map;
import java.util.Optional;

@Repository
public class AuthenticationTokenDaoImpl extends GenericDaoImpl<AuthToken, AuthTokenId> implements AuthenticationTokenDao {

        @Override
        public Optional<AuthToken> findAuthenticationTokenById(AuthTokenId authTokenId) throws HibernateException {
                return Optional.of(findById(authTokenId));
        }

        @Override
        public Optional<AuthToken> findAuthenticationTokenByTokenId(Map<TableProperty, Object> filterCriteria) throws HibernateException {
                return findByFilterCriteria(filterCriteria).stream().findFirst();
        }

        @Override
        public void updateAuthenticationToken(AuthToken authToken) throws HibernateException {
                makePersistent(authToken);
        }


}
