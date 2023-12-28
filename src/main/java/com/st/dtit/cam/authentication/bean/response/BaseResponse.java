package com.st.dtit.cam.authentication.bean.response;

import lombok.Data;

@Data
public class BaseResponse<T> {

     private int statusCode;

     private String statusMessage;

     private  T data;
}
