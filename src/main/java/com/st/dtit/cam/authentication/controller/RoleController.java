package com.st.dtit.cam.authentication.controller;

import com.st.dtit.cam.authentication.bean.param.RoleQueryParameter;
import com.st.dtit.cam.authentication.bean.response.Response;
import com.st.dtit.cam.authentication.enums.ResponseCode;
import com.st.dtit.cam.authentication.enums.TableProperty;
import com.st.dtit.cam.authentication.service.RoleQueryService;
import com.st.dtit.cam.authentication.utility.builder.RoleCriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class RoleController extends BaseController{

        private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

        @Autowired
        private RoleQueryService roleQueryService;

        @PostMapping(value="/api/v1/cam/authentication/role/fetch-by-criteria", produces="application/json")
        public ResponseEntity<Response> getRolesByFilterCriteria(@RequestBody RoleQueryParameter parameter,
                                                                 HttpServletRequest request) {
               LOGGER.info("api::fetch user role by criteria::{}", parameter);
               final String jwtToken = getJwtToken(request);

               final Map<TableProperty, Object> filterCriteria = new RoleCriteriaBuilder().withQueryParameter(parameter)
                                                                                          .build();
               Response response = null;
               try {
                      response = roleQueryService.getRolesByFilterCriteria(filterCriteria, jwtToken);
               } catch (Exception ex) {
                      LOGGER.error("error::api::fetch user role by criteria::{}", parameter, ex);
                      return getExceptionResponse(ResponseCode.APPLICATION_EXCEPTION,
                                                  new Object[]{ex.getMessage(), ex.getStackTrace()});
               }
               return this.getResponse(response);
        }
}
