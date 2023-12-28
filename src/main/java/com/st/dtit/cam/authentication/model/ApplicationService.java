package com.st.dtit.cam.authentication.model;


import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.stereotype.Service;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "AUTH_SERVICES")
@Cacheable
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE, region="auth_services")
public class ApplicationService implements Serializable {

        @Id
        @Column(name = "SERVICE_CODE")
        private String serviceCode;

        @Column(name = "SERVICE_NAME")
        private String serviceName;

        @Column(name = "ACTIVE")
        private Boolean active;

}



