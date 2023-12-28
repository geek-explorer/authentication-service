package com.st.dtit.cam.authentication.bean.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleQueryParameter implements Serializable {

        private static final long serialVersionUID = -2061169364930819340L;

        private RoleFilterCriteria filterCriteria;

}
