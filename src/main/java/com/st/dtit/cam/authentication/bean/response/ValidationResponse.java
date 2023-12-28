package com.st.dtit.cam.authentication.bean.response;

import com.st.dtit.cam.authentication.enums.ResponseCode;
import lombok.Data;

@Data
public class ValidationResponse {

    private ResponseCode responseCode;

    private String responseMessage;

    private Object data;

    private ValidationResponse(){}

    public ValidationResponse(ResponseCode responseCode, String responseMessage, Object data){
           this.responseCode    = responseCode;
           this.responseMessage = responseMessage;
           this.data = data;
    }

}
