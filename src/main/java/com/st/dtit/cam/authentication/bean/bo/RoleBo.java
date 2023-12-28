package com.st.dtit.cam.authentication.bean.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleBo implements Serializable {

        private static final long serialVersionUID = 3150478152166084725L;

        private String roleId;

        private String roleDescription;

        private Boolean active;

}
