package com.st.dtit.cam.authentication.service.impl;

import com.st.dtit.cam.authentication.bean.AuthorityBean;
import com.st.dtit.cam.authentication.bean.response.JwtRefreshResponse;
import com.st.dtit.cam.authentication.bean.response.JwtResponse;
import com.st.dtit.cam.authentication.bean.response.Response;
import com.st.dit.cam.auth.constant.CommonAuthConstant;
import com.st.dtit.cam.authentication.constant.PropertyField;
import com.st.dtit.cam.authentication.constant.ResponseMessage;
import com.st.dtit.cam.authentication.dao.ApplicationDao;
import com.st.dit.cam.auth.enums.AuthorityEnum;
import com.st.dtit.cam.authentication.dao.AuthenticationTokenDao;
import com.st.dtit.cam.authentication.dao.UserDao;
import com.st.dtit.cam.authentication.dao.UserRoleDao;
import com.st.dtit.cam.authentication.enums.ResponseCode;
import com.st.dtit.cam.authentication.enums.TableProperty;
import com.st.dtit.cam.authentication.model.Application;
import com.st.dtit.cam.authentication.model.ApplicationUser;
import com.st.dtit.cam.authentication.model.AuthToken;
import com.st.dtit.cam.authentication.model.UserRoles;
import com.st.dtit.cam.authentication.model.composite.AuthTokenId;
import com.st.dtit.cam.authentication.service.JwtService;
import com.st.dit.cam.auth.utility.JwtTokenUtil;
import com.st.dtit.cam.authentication.utility.builder.JwtRefreshBuilder;
import com.st.dtit.cam.authentication.utility.builder.JwtResponseBuilder;
import com.st.dtit.cam.authentication.utility.factory.ResponseFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtServiceImpl.class);

    public static final long JWT_REFRESH_TOKEN  = 12 * 60 * 60;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private AuthenticationTokenDao authenticationTokenDao;

    @Value("${spring.profiles.active}")
    private String applicationInstance;

    @Override
    public Response generateJwToken(final String username, final String authCredential,
                                    final String  applicationCode) throws Exception {

             final Map<TableProperty, Object> filterCriteria = new HashMap<>();
             filterCriteria.put(TableProperty.USER_USERNAME, username);

             final List<ApplicationUser> systemUser = userDao.findUser(filterCriteria);

             if (CollectionUtils.isEmpty(systemUser)){
                   LOGGER.warn("caution::generate jwt token::user not configured to authorized access");
                   return ResponseFactory.getResponse(ResponseCode.VALIDATION_ERROR, ResponseMessage.ERROR_INVALID_USER,new String[]{username});
             }
             final Application application = applicationDao.findApplicationById(applicationCode);
             if (null == application) {
                   LOGGER.warn("caution::generate jwt token::application is no configured to authorized access");
                   return ResponseFactory.getResponse(ResponseCode.VALIDATION_ERROR, ResponseMessage.ERROR_INVALID_APPS,new String[]{applicationCode});
            }

            final ApplicationUser applicationUser = systemUser.get(0);
            final List<AuthorityBean> authorities = getUserAuthorities(applicationUser, application.getApplicationCode());

            final UserDetails userDetail = new User(
                   application.getApplicationCode() + CommonAuthConstant.JWT_USER_ENTITY_SEPARATOR +
                             applicationInstance + CommonAuthConstant.JWT_USER_ENTITY_SEPARATOR + username,
                             authCredential, authorities);

           final String token = jwtTokenUtil.generateToken(userDetail);

           final String refreshToken = updateAuthenticationToken(application.getApplicationCode(), applicationUser.getUserId());

           final JwtResponse jwtResponse = new JwtResponseBuilder().withUserName(applicationUser.getUserName())
                                                                   .withName(applicationUser.getLastName(), applicationUser.getFirstName())
                                                                   .withApplicationName(applicationCode)
                                                                   .hasAuthorities(authorities.stream().map(AuthorityBean::getAuthority).collect(Collectors.toList()))
                                                                   .withJwToken(token)
                                                                   .withRefreshToken(refreshToken)
                                                                   .build();

           return ResponseFactory.getResponse(ResponseCode.OK.getCode(), ResponseCode.OK.getMessage(), jwtResponse);
    }

    @Override
    public Response getRefreshToken(final String token) throws Exception {

           if (StringUtils.isEmpty(token)) {
                 LOGGER.warn("caution::fetch refresh token::missing required field: {}", TableProperty.TOKEN_ID.getDescription());
                 return ResponseFactory.getResponse(ResponseCode.VALIDATION_ERROR, ResponseMessage.ERROR_REQUIRED_PARAMETER, new String[]{TableProperty.TOKEN_ID.getDescription()});
           }

           Map<TableProperty, Object> filterCriteria = new HashMap<>();
           filterCriteria.put(TableProperty.TOKEN_ID, token);

           final Optional<AuthToken>  authToken =  authenticationTokenDao.findAuthenticationTokenByTokenId(filterCriteria);
           if (!authToken.isPresent()) {
                  LOGGER.warn("caution::fetch refresh token::entity not found: {}", PropertyField.ENTITY_REFRESH_TOKEN);
                  return ResponseFactory.getResponse(ResponseCode.NO_DATA_FOUND, ResponseMessage.ERROR_ENTITY_NOT_EXIST, new String[]{PropertyField.ENTITY_REFRESH_TOKEN, token});
           }

           if (verifyTokenExpiration(authToken.get())){
                   LOGGER.warn("caution::fetch refresh token::refresh token expired: {}", token);
                   return ResponseFactory.getResponse(ResponseCode.NO_DATA_FOUND, ResponseMessage.ERROR_TOKEN_EXPIRED, new String[]{PropertyField.ENTITY_REFRESH_TOKEN, token});
           }

           final ApplicationUser applicationUser = userDao.findById(authToken.get().getAuthTokenId().getUserId());
           final List<AuthorityBean> authorities = getUserAuthorities(applicationUser, authToken.get().getAuthTokenId().getApplicationCode());

            if (null == applicationUser){
                   LOGGER.warn("caution::fetch refresh token::user not configured to authorized access");
                   return ResponseFactory.getResponse(ResponseCode.VALIDATION_ERROR, ResponseMessage.ERROR_INVALID_USER,new String[]{authToken.get().getAuthTokenId().getUserId()});
            }

            final UserDetails userDetail = new User(
                      authToken.get().getAuthTokenId().getApplicationCode() + CommonAuthConstant.JWT_USER_ENTITY_SEPARATOR +
                                applicationInstance + CommonAuthConstant.JWT_USER_ENTITY_SEPARATOR + applicationUser.getUserName(),
                                StringUtils.EMPTY, authorities);

            final String newToken = jwtTokenUtil.generateToken(userDetail);

            final JwtRefreshResponse jwtRefreshResponse = new JwtRefreshBuilder()
                                                                         .withUserName(applicationUser.getUserName())
                                                                         .withRefreshToken(token)
                                                                         .withJwtToken(newToken)
                                                                         .withAuthorities(authorities.stream().map(auth -> auth.getAuthority()).toArray(String[]::new))
                                                                         .build();
            return ResponseFactory.getResponse(ResponseCode.OK.getCode(), ResponseCode.OK.getMessage(), jwtRefreshResponse);
    }

    private Boolean verifyTokenExpiration(AuthToken authToken) {
            if (authToken.getExpiryDate().compareTo(Instant.now()) < 0) {
                   authenticationTokenDao.delete(authToken);
                   return true;
            }
            return false;
    }


    private String updateAuthenticationToken(String applicationCode, String userId) throws HibernateException{
            AuthTokenId authTokenId = new AuthTokenId(applicationCode, userId);

            AuthToken authToken = new AuthToken();
            authToken.setAuthTokenId(authTokenId);
            authToken.setToken(UUID.randomUUID().toString());
            authToken.setExpiryDate(Instant.now().plusMillis(JWT_REFRESH_TOKEN * 1000));

            authenticationTokenDao.updateAuthenticationToken(authToken);
            return authToken.getToken();
    }


    private List<AuthorityBean> getUserAuthorities(final ApplicationUser applicationUser, final String applicationCode) throws HibernateException {

             final Map<TableProperty, Object> filterCriteria = new HashMap<>();
             filterCriteria.put(TableProperty.UR_USER, applicationUser);
             filterCriteria.put(TableProperty.UR_APPLICATION,  applicationCode);

             List<AuthorityBean> authorities = new ArrayList<>();

             final List<UserRoles> userRoles = userRoleDao.findUserRoles(filterCriteria);
             if (CollectionUtils.isNotEmpty(userRoles)){
                    authorities = userRoles.stream()
                                           .map(userRole -> new AuthorityBean(userRole.getRole().getAppRoleId().getRoleId()))
                                           .collect(Collectors.toList());
             }else {
                   authorities.add(new AuthorityBean(AuthorityEnum.GUEST.getCode()));
             }
             return authorities;
    }

}
