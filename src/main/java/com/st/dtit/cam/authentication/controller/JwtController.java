package com.st.dtit.cam.authentication.controller;

import com.st.dtit.cam.authentication.bean.param.AuthenticationParameter;
import com.st.dtit.cam.authentication.bean.param.RefreshTokenParameter;
import com.st.dtit.cam.authentication.bean.response.Response;
import com.st.dtit.cam.authentication.enums.ResponseCode;
import com.st.dtit.cam.authentication.service.ApplicationUserService;
import com.st.dtit.cam.authentication.service.JwtService;
import com.st.dit.cam.auth.utility.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController extends BaseController {

     private static final Logger LOGGER = LoggerFactory.getLogger(JwtController.class);

     @Autowired
     private JwtService jwtService;

     @Autowired
     private JwtTokenUtil jwtTokenUtil;

     @Autowired
     private ApplicationUserService applicationUserService;

     @RequestMapping(value = "/api/v1/cam/authentication/jwt-auth/apps={appsname}", method = RequestMethod.GET)
     public ResponseEntity<Response> generateJWToken(@PathVariable("appsname") String applicationName) throws Exception {
             final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
             final User activeUser = (User) auth.getPrincipal();
             final String authCredential = (String) auth.getCredentials();
             LOGGER.info("api::generate jwttoken::user: {}, application: {}", activeUser.getUsername(), applicationName);

             Response response = null;
             try{
                  response = jwtService.generateJwToken(activeUser.getUsername(),authCredential,applicationName);
             }catch (Exception ex){
                  LOGGER.error("error::generate jwttoken::user: {}, application: {}" , activeUser.getUsername(), applicationName, ex);
                  return getExceptionResponse(ResponseCode.APPLICATION_EXCEPTION,
                                              new Object[]{ex.getMessage(), ex.getStackTrace()});
             }
             return  this.getResponse(response);
     }

     @PostMapping(value = "/api/v1/cam/authentication/jwt-auth/refresh-token", produces = "application/json")
     public ResponseEntity<Response> refreshJWToken(@RequestBody RefreshTokenParameter parameter)  {
             final String refreshToken = parameter.getRefreshToken();
             LOGGER.debug("api::refresh jwttoken::parameter: {}", parameter);

             Response response = null;
             try {
                 response = jwtService.getRefreshToken(refreshToken);
             }catch (Exception ex) {
                 LOGGER.error("error::refresh jwttoken:: {}" , refreshToken, ex);
                 return getExceptionResponse(ResponseCode.APPLICATION_EXCEPTION,
                                             new Object[]{ex.getMessage(), ex.getStackTrace()});
             }
             return this.getResponse(response);
     }

     @PostMapping(value = "/api/v1/cam/authentication/jwt-auth/authenticate", produces = "application/json")
     public ResponseEntity<Response> authenticateUser(@RequestBody AuthenticationParameter parameter)  {
             LOGGER.debug("api::authenticate user :: parameter: {}", parameter);
             Response response = null;
             try{
                    response =  applicationUserService.getUserDetailsAndRoles(parameter.getUserName(), parameter.getApplicationCode());
             }catch (Exception ex){
                    LOGGER.error("error::authenticate user::parameter: {}} ", parameter, ex);
                    return getExceptionResponse(ResponseCode.APPLICATION_EXCEPTION,
                                                new Object[]{ex.getMessage(), ex.getStackTrace()});
             }
             return this.getResponse(response);
     }

}
