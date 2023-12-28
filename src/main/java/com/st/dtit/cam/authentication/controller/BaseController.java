package com.st.dtit.cam.authentication.controller;

import com.st.dtit.cam.authentication.bean.response.Response;
import com.st.dtit.cam.authentication.enums.ResponseCode;
import com.st.dtit.cam.authentication.utility.factory.ResponseFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

       public static final String CONTENT_TYPE  = "Content-Type";
       public static final String CONTENT_VALUE = "application/json;charset=UTF-8";
       public static final String RESPONDED     = "Responded";

       private static final String REQUEST_TOKEN_KEY    = "Authorization";
       private static final String PREFIX_TOKEN_KEY     = "Token ";

       public HttpHeaders getHeader(){
                HttpHeaders headers = new HttpHeaders();
                headers.add(CONTENT_TYPE, CONTENT_VALUE);
                headers.add(RESPONDED, this.getClass().getSimpleName());
                return headers;
       }

        public String getJwtToken(HttpServletRequest request) {
                final String  requestTokenHeader = request.getHeader(REQUEST_TOKEN_KEY);
                if (StringUtils.isNotEmpty(requestTokenHeader) && requestTokenHeader.startsWith(PREFIX_TOKEN_KEY)){
                     return requestTokenHeader.substring(6);
                }
                return StringUtils.EMPTY;
       }

       public ResponseEntity<Object> getResponse(Object response){
               return ResponseEntity.ok().headers(getHeader()).body(response);
       }

       public ResponseEntity<Response> getResponse(Response response){
              return ResponseEntity.ok().headers(getHeader()).body(response);
       }

       public ResponseEntity<Response> getNoDataResponse(){return getNoDataResponse(ResponseCode.NO_CONTENT, null);}

       public ResponseEntity<Response> getNoDataResponse(final ResponseCode responseCode, final String message){
              final Response response = ResponseFactory.getResponse(responseCode.getCode(), responseCode.getMessage(), message);
              return getResponse(response);
       }


       public ResponseEntity<Response> getExceptionResponse(ResponseCode responseCode, Object result){
              if (null != responseCode) {
                  Response response = ResponseFactory.getResponse(responseCode.getCode(), responseCode.getMessage(), result);
                  return getResponse(response);
              } else {
                  return getNoDataResponse();
              }
       }

}
