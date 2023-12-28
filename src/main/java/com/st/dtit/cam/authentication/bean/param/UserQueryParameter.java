package com.st.dtit.cam.authentication.bean.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserQueryParameter implements Serializable {

        private static final long serialVersionUID = 1275312140562950202L;

        private UserFilterCriteria filterCriteria;

}
