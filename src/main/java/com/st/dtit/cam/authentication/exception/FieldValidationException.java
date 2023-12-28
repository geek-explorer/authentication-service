package com.st.dtit.cam.authentication.exception;


import com.google.common.collect.ImmutableList;
import com.st.dtit.cam.authentication.bean.response.ValidationResponse;

import java.util.List;

public class FieldValidationException extends RuntimeException {

    private static final long serialVersionUID = 7663705380621670946L;

    private List<ValidationResponse> validationResponse;

    public FieldValidationException(String message){
          super(message);
    }


    public FieldValidationException(String message, final ValidationResponse validationResponse){
           super(message);
           this.validationResponse = ImmutableList.of(validationResponse);
    }

    public FieldValidationException(String message, final List<ValidationResponse> validationResponseList) {
        super(message);
        this.validationResponse = validationResponseList;
    }

    public List<ValidationResponse> getResponse(){
        return validationResponse;
    }
}
