package com.st.dtit.cam.authentication.model.composite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class AuthTokenId implements Serializable {

        private static final long serialVersionUID = 3508562465784510249L;

        @Column(name = "APP_CODE")
        private String applicationCode;

        @Column(name = "USER_ID")
        private String userId;
}
