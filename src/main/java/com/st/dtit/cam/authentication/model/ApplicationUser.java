package com.st.dtit.cam.authentication.model;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "AUTH_USERS")
@Cacheable
@Cache(usage= CacheConcurrencyStrategy.READ_ONLY, region="application_user")
public class ApplicationUser implements Serializable {

        private static final long serialVersionUID = -7347662020030393957L;

        @Id
        @Column(name = "USER_ID")
        private String userId;

        @Column(name = "USER_NAME")
        private String userName;

        @Column(name = "FIRST_NAME")
        private String firstName;

        @Column(name = "LAST_NAME")
        private String lastName;

        @Column(name = "MIDDLE_NAME")
        private String middleName;

        @Column(name = "EMAIL_ADDRESS")
        private String emailAddress;

        @Column(name = "ACTIVE")
        private Boolean active;

        @Column(name = "CREATED_DATE")
        private Date dateCreated;

        @Column(name = "CREATED_BY")
        private String createdBy;

        @Column(name = "LAST_UPDATED_DATE")
        private Date lastUpdateDate;

        @Column(name = "LAST_UPDATED_BY")
        private String lastUpdateBy;
}
