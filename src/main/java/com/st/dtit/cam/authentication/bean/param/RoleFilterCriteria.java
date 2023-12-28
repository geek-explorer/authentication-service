package com.st.dtit.cam.authentication.bean.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleFilterCriteria implements Serializable {

        private static final long serialVersionUID = 8529847996502320628L;

        private String roleId;

        private String[] roleIds;

        private String applicationCode;

        private String[] applicationCodes;

        private String roleDescription;

        private String[] roleDescriptions;

}
