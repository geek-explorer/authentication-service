package com.st.dtit.cam.authentication.bean.response;


import lombok.Data;

@Data
public class ServiceResponse {

    private Integer responseCode;

    private String responseMessage;

    private String[] data;
}
