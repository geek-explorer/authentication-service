package com.st.dtit.cam.authentication.controller;

import com.st.dtit.cam.authentication.bean.param.ApplicationUserParameter;
import com.st.dtit.cam.authentication.bean.param.UserQueryParameter;
import com.st.dtit.cam.authentication.bean.response.Response;
import com.st.dtit.cam.authentication.enums.ResponseCode;
import com.st.dtit.cam.authentication.enums.TableProperty;
import com.st.dtit.cam.authentication.service.UserQueryService;
import com.st.dtit.cam.authentication.service.UserUpdateService;
import com.st.dtit.cam.authentication.utility.builder.UserCriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class UserController extends BaseController {

        private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

        @Autowired
        private UserQueryService userQueryService;

        @Autowired
        @Qualifier("authUserCreate")
        private UserUpdateService userCreateService;

        @PostMapping(value="/api/v1/cam/authentication/authentication-user/create", produces="application/json")
        public ResponseEntity<Response>  createApplicationUser(@RequestBody ApplicationUserParameter parameter,
                                                               HttpServletRequest request) {
                 LOGGER.debug("api::add authentication user::parameter: {}", parameter);
                 final String jwtToken = getJwtToken(request);

                 Response response = null;
                 try {
                     response = userCreateService.updateApplicationUser(parameter, jwtToken);
                 } catch (Exception ex) {
                     LOGGER.error("error::add authentication user:", ex);
                     return getExceptionResponse(ResponseCode.APPLICATION_EXCEPTION,
                                                 new Object[]{ex.getMessage(), ex.getStackTrace()});
                 }
                 return this.getResponse(response);
        }

        @GetMapping(value="/api/v1/cam/authentication/authentication-user/fetch-all", produces="application/json")
        public ResponseEntity<Response> getAllUsers(HttpServletRequest request) {
                 LOGGER.debug("api::fetch all authentication users");
                 final String jwtToken = getJwtToken(request);

                 Response response = null;
                 try {
                      response = userQueryService.getAllAuthenticationUsers(jwtToken);
                 } catch (Exception ex) {
                      LOGGER.error("error::api::fetch all authentication users", ex);
                      return getExceptionResponse(ResponseCode.APPLICATION_EXCEPTION,
                                                  new Object[]{ex.getMessage(), ex.getStackTrace()});
                 }
                 return this.getResponse(response);
        }

        @PostMapping(value="/api/v1/cam/authentication/authentication-user/fetch-by-criteria", produces="application/json")
        public ResponseEntity<Response> getUsersByFilterCriteria(@RequestBody UserQueryParameter parameter,
                                                                 HttpServletRequest request) {
                LOGGER.debug("api::fetch authentication users by criteria::{}", parameter);
                final String jwtToken = getJwtToken(request);

                final Map<TableProperty, Object> filterCriteria = new UserCriteriaBuilder().withQueryParameter(parameter)
                                                                                          .build();
                Response response = null;
                try {
                      response  = userQueryService.getAuthenticationUsersByFilterCriteria(filterCriteria, jwtToken);
                } catch (Exception ex) {
                      LOGGER.error("error::api::fetch authentication users by criteria::{}", parameter, ex);
                      return getExceptionResponse(ResponseCode.APPLICATION_EXCEPTION,
                                                  new Object[]{ex.getMessage(), ex.getStackTrace()});
                }
                return this.getResponse(response);
        }

}
