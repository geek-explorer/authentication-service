package com.st.dtit.cam.authentication.bean.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApplicationServiceBo implements Serializable {

        private static final long serialVersionUID = -1651397042071400884L;

        private String serviceCode;

        private String serviceName;

        private Boolean active;

}
