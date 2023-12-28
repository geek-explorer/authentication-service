package com.st.dtit.cam.authentication.service;

import com.st.dit.cam.auth.utility.JwtTokenUtil;
import com.st.dtit.cam.authentication.bean.response.Response;
import com.st.dtit.cam.authentication.bean.response.ValidationResponse;
import com.st.dtit.cam.authentication.enums.ResponseCode;
import com.st.dtit.cam.authentication.utility.factory.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class BaseService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    protected String getUserFromToken(final String jwtToken) {
           return jwtTokenUtil.getSystemUserFromToken(jwtToken);
    }

    protected Response getValidationErrorResponse(final String responseMessage, final String fieldName){
           return ResponseFactory.getResponse(ResponseCode.VALIDATION_ERROR, responseMessage, fieldName);
    }

    protected Response getResponse(final Object result){
           return ResponseFactory.getResponse(ResponseCode.OK.getCode(), ResponseCode.OK.getMessage(), result);
    }

    protected Response getEmptyDataResponse(){
           return ResponseFactory.getEmptyDataResponse(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
    }

    protected Response validationErrorResponse(final List<ValidationResponse> validationResponse) {
           return  ResponseFactory.getResponse(ResponseCode.VALIDATION_ERROR.getCode(), ResponseCode.VALIDATION_ERROR.getMessage(), validationResponse);
    }
}
