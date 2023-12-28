package com.st.dtit.cam.authentication.model;

import com.st.dtit.cam.authentication.model.composite.AppRoleId;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "AUTH_ROLES")
@Cacheable
@Cache(usage= CacheConcurrencyStrategy.READ_ONLY, region="role")
public class Role implements Serializable {

        private static final long serialVersionUID = 6529223258142704869L;

        @EmbeddedId
        private AppRoleId appRoleId;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "APP_CODE", insertable = false, updatable = false)
        private Application application;

        @Column(name = "ROLE_DESCRIPTION")
        private String roleDescription;

        @Column(name = "ACTIVE")
        private Boolean active;

}
