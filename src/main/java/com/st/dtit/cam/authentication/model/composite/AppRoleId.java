package com.st.dtit.cam.authentication.model.composite;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class AppRoleId implements Serializable {

        private static final long serialVersionUID = 6540292004300729932L;

        @Column(name = "ROLE_ID")
        private String roleId;

        @Column(name = "APP_CODE")
        private String applicationCode;

}
