package com.st.dtit.cam.authentication.bean.response;


import com.st.dtit.cam.authentication.enums.ResponseCode;

import java.io.Serializable;

public class Response extends BaseResponse<Object> implements Serializable {

      public static final String SUCCESS_MESSAGE = "OK";

      public static final String NO_DATA_FOUND = "No Content";

      public Response(){}

      public Response(final ResponseCode responseCode){
            this.setStatusCode(responseCode.getCode());
            this.setStatusMessage(responseCode.getMessage());
      }
}
