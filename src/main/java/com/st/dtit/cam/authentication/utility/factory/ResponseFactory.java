package com.st.dtit.cam.authentication.utility.factory;


import com.st.dtit.cam.authentication.bean.response.Response;
import com.st.dtit.cam.authentication.bean.response.ServiceResponse;
import com.st.dtit.cam.authentication.enums.ResponseCode;

public class ResponseFactory {

    private ResponseFactory(){}

    public static Response getResponse(int code, String message, Object data){
          Response response = new Response();
          response.setStatusCode(code);
          response.setStatusMessage(message);
          response.setData(data);
          return response;
    }

    public static Response getResponse(final ResponseCode responseCode, final String responseMessage,
                                       final String... data){
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setResponseCode(responseCode.getCode());
        serviceResponse.setResponseMessage(responseMessage);
        serviceResponse.setData(data);
        return getResponse(responseCode.getCode(), responseCode.getMessage(),
                serviceResponse);
    }

    public static Response getEmptyDataResponse(int code, String message){
          return getResponse(code, message, null);
    }


}
